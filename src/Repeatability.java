import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public interface Repeatability {

    static void showTask(String date) {
        LocalDate inputDate = readDate(date);
        for (Task one : TaskService.getAllTasks().values()) {
            switch (one.getRepeatability()) {
                case "Единократная":
                    single(one, inputDate);
                    break;
                case "Ежедневная":
                    daily(one, inputDate);
                    break;
                case "Еженедельная":
                    weekly(one, inputDate);
                    break;
                case "Ежемесячная":
                    monthly(one, inputDate);
                    break;
                case "Ежегодная":
                    annual(one, inputDate);
                    break;
            }
        }

    }

    static LocalDate readDate(String date) {
        Scanner scanner = new Scanner(System.in);
        try {
            String[] splitDate = date.split("\\.");
            int day = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int year = Integer.parseInt(splitDate[2]);
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("Попробуй ещё раз: ");
        }
        return readDate(scanner.next());
    }

    static void single(Task one, LocalDate inputDate) {
        if (one.getDueDate().toLocalDate().isEqual(inputDate)) {
            System.out.println(one);
        }
    }

    static void daily(Task one, LocalDate inputDate) {
        LocalDateTime primalDate = one.getDueDate();
        LocalDateTime newDate = one.getDueDate();
        while (newDate.toLocalDate().isBefore(inputDate) || newDate.toLocalDate().isEqual(inputDate)) {
            if (newDate.toLocalDate().isEqual(inputDate)) {
                one.setDueDate(newDate);
                System.out.println(one);
                break;
            }
            newDate = newDate.plusDays(1);
        }
        one.setDueDate(primalDate);
    }

    static void weekly(Task one, LocalDate inputDate) {
        LocalDateTime primalDate = one.getDueDate();
        LocalDateTime newDate = one.getDueDate();
        while (newDate.toLocalDate().isBefore(inputDate) || newDate.toLocalDate().isEqual(inputDate)) {
            if (newDate.toLocalDate().isEqual(inputDate)) {
                one.setDueDate(newDate);
                System.out.println(one);
                break;
            }
            newDate = newDate.plusWeeks(1);
        }
        one.setDueDate(primalDate);
    }

    static void monthly(Task one, LocalDate inputDate) {
        LocalDateTime primalDate = one.getDueDate();
        LocalDateTime newDate = one.getDueDate();
        while (newDate.toLocalDate().isBefore(inputDate) || newDate.toLocalDate().isEqual(inputDate)) {
            if (newDate.toLocalDate().isEqual(inputDate)) {
                one.setDueDate(newDate);
                System.out.println(one);
                break;
            }
            newDate = newDate.plusMonths(1);
        }
        one.setDueDate(primalDate);
    }

    static void annual(Task one, LocalDate inputDate) {
        LocalDateTime primalDate = one.getDueDate();
        LocalDateTime newDate = one.getDueDate();
        while (newDate.toLocalDate().isBefore(inputDate) || newDate.toLocalDate().isEqual(inputDate)) {
            if (newDate.toLocalDate().isEqual(inputDate)) {
                one.setDueDate(newDate);
                System.out.println(one);
                break;
            }
            newDate = newDate.plusYears(1);
        }
        one.setDueDate(primalDate);
    }


}
