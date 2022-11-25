import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task>, Cloneable{

    private String header;
    private String description;
    private String type;
    private LocalDateTime dueDate;
    private String repeatability;
    private boolean actuality = true;
    private int id;
    private static int count = 1;

    public Task(String header, String description, String type, LocalDateTime dateOfCreation, String repeatability) {
        setHeader(header);
        setDescription(description);
        this.type = type;
        this.dueDate = dateOfCreation;
        this.repeatability = repeatability;
        this.id = count;
        count++;
    }



    public void setActuality(boolean actuality) {
        this.actuality = actuality;
    }

    public void setHeader(String header) {
        if (header != null && !header.isBlank()) {
            this.header = header;
        } else {
            throw new IllegalArgumentException("Вы ввели некорректый заголовок");
        }
    }

    public void setDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Вы ввели некорректное описание");
        }
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getRepeatability() {
        return repeatability;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(header, task.header) && Objects.equals(description, task.description) && Objects.equals(type, task.type) && Objects.equals(dueDate, task.dueDate) && Objects.equals(repeatability, task.repeatability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, description, type, dueDate, repeatability, id);
    }

    @Override
    public String toString() {
        String status;
        if (this.actuality) {
            status = "(Актуальная)";
        } else {
            status = "(Не актуальная)";
        }
        return "===================================================\n" +
                "Название: " + this.header + "\nОписание: " + this.description + "\nТип: " + this.type + "   |   " + this.repeatability + "   |   " + status + "\nДата и время: " + this.dueDate.toLocalDate() + "   " + this.dueDate.getHour() + ":" + this.dueDate.getMinute() + "          [ID: " + this.id + "]"
                + "\n===================================================";
    }

    @Override
    public int compareTo(Task that) {
        return this.dueDate.compareTo(that.dueDate);
    }

    @Override
    protected Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


}
