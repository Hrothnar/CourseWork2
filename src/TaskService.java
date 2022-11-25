import java.time.LocalDateTime;
import java.util.*;

public class TaskService implements Repeatability {
    private static Map<Integer, Task> allTasks = new HashMap();
    private static Map<Integer, Task> archive = new LinkedHashMap<>();

    public static Map<Integer, Task> getAllTasks() {
        return allTasks;
    }

    public static Map<Integer, Task> getArchive() {
        return archive;
    }

    public static void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            System.out.println("Введите ID задачи: ");
                            changeActuality(scanner.nextInt());
                            break;
                        case 3:
                            System.out.println("Введите интересующую вас дату (в формате dd.mm.yyyy): ");
                            Repeatability.showTask(scanner.next());
                            break;
                        case 4:
                            showArchive();
                            break;
                        case 5:
                            showAllTasks();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        try {
            System.out.print("Введите название задачи: ");
            scanner.nextLine();
            String taskHeader = scanner.nextLine();
            System.out.print("Введите описание задачи: ");
            String taskDescription = scanner.nextLine();
            System.out.print("Укажите тип задачи задачи:\n1.Личная\n2.Рабочая\n");
            int type = scanner.nextInt();
            String taskType = chooseType(type);
            System.out.print("Укажите переодичность задачи:\n1.Единократная\n2.Ежедневная\n3.Еженедельная\n4.Ежемесячная\n5.Ежегодная\n");
            String taskRepeatability = chooseRepeatability(scanner.nextInt());
            System.out.println("Укажите дату и время выполнения задачи (в формате dd.mm.yyyy.hh:mm): ");
            LocalDateTime time = transformation(scanner.next());
            Task newTask = new Task(taskHeader, taskDescription, taskType, time, taskRepeatability);
            TaskService.getAllTasks().put(newTask.getId(), newTask);
            edit(newTask, scanner);
        } catch (Exception e) {
            System.out.println("Ошибочка...ещё раз.");
            inputTask(scanner);
        }
    }


    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n4. Заглянуть в архив\n5. Показать все задачи\n0. Выход");
    }

    private static String chooseType(int num) {
        String type = null;
        switch (num) {
            case 1:
                type = "Личная";
                break;
            case 2:
                type = "Рабочая";
                break;
            default:
                Scanner scanner = new Scanner(System.in);
                System.out.println("Выберите тип задачи: ");
                chooseType(scanner.nextInt());
        }
        return type;
    }

    private static String chooseRepeatability(int num) {
        String repeatability = null;
        switch (num) {
            case 1:
                repeatability = "Единократная";
                break;
            case 2:
                repeatability = "Ежедневная";
                break;
            case 3:
                repeatability = "Еженедельная";
                break;
            case 4:
                repeatability = "Ежемесячная";
                break;
            case 5:
                repeatability = "Ежегодная";
                break;
            default:
                Scanner scanner = new Scanner(System.in);
                System.out.println("Выберите переодичность задачи: ");
                chooseRepeatability(scanner.nextInt());
        }
        return repeatability;
    }

    private static LocalDateTime transformation(String some) {
        Scanner scanner = new Scanner(System.in);
        try {
            String[] date = some.split("\\.");
            String[] time = date[3].split(":");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (Exception e) {
            System.out.println("Попробуй снова: ");
        }
        return transformation(scanner.next());
    }

    private static void changeActuality(int key) {
        if (getAllTasks().containsKey(key)) {
            getAllTasks().get(key).setActuality(false);
            getArchive().put(key, getAllTasks().get(key));
            System.out.println("Задача (ID: " + key + ") не актуальна");
        } else {
            System.out.println("Такой задачи не нашлось");
        }
    }

    private static void showArchive() {
        if (getArchive().isEmpty()) {
            System.out.println("Архив пуст, видимо вы не выполнили ни одной задачи");
        } else {
            for (Task one : getArchive().values()) {
                System.out.println(one);
            }
        }
    }

    private static void edit(Task newTask, Scanner scanner) {
        try {
            System.out.println("Хотите отредактировать задачу? (Y/N): ");
            String decision = scanner.next();
            if (decision.toLowerCase().equals("y")) {
                System.out.print("Введите название задачи: ");
                scanner.nextLine();
                String taskHeader = scanner.nextLine();
                newTask.setHeader(taskHeader);
                System.out.print("Введите описание задачи: ");
                String taskDescription = scanner.nextLine();
                newTask.setDescription(taskDescription);
            } else if (decision.toLowerCase().equals("n")) {
            } else {
                throw new IllegalArgumentException("Промахнулся");
            }
        } catch (Exception e) {
            System.out.println("Не промахнись");
            edit(newTask, scanner);
        }
    }

    private static List<Task> getTaskList() {
        List<Task> taskList = new LinkedList<>();
        for (Task one : getAllTasks().values()) {
            LocalDateTime primalDate = one.getDueDate();
            LocalDateTime newDate = one.getDueDate();
            switch (one.getRepeatability()) {
                case "Единократная":
                    taskList.add(one.clone());
                    break;
                case "Ежедневная":
                    while (newDate.isBefore(LocalDateTime.now())) {
                        newDate = newDate.plusDays(1);
                    }
                    one.setDueDate(newDate);
                    taskList.add(one.clone());
                    one.setDueDate(primalDate);
                    break;
                case "Еженедельная":
                    while (newDate.isBefore(LocalDateTime.now())) {
                        newDate = newDate.plusWeeks(1);
                    }
                    one.setDueDate(newDate);
                    taskList.add(one.clone());
                    one.setDueDate(primalDate);
                    break;
                case "Ежемесячная":
                    while (newDate.isBefore(LocalDateTime.now())) {
                        newDate = newDate.plusMonths(1);
                    }
                    one.setDueDate(newDate);
                    taskList.add(one.clone());
                    one.setDueDate(primalDate);
                    break;
                case "Ежегодная":
                    while (newDate.isBefore(LocalDateTime.now())) {
                        newDate = newDate.plusYears(1);
                    }
                    one.setDueDate(newDate);
                    taskList.add(one.clone());
                    one.setDueDate(primalDate);
                    break;
            }
        }
        return taskList;
    }

    private static void showAllTasks() {
        List<Task> taskList = getTaskList();
        Collections.sort(taskList);
        for (Task one : taskList) {
            System.out.println(one);
        }
    }


}

