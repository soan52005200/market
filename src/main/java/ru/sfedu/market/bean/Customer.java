package ru.sfedu.market.bean;

public class Customer {

    private Long id;
    private String fio;
    private Integer age;

    public Customer(Long id, String fio, Integer age) {
        this.id = id;
        this.fio = fio;
        this.age = age;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", age=" + age +
                '}';
    }
}
