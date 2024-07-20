package org.example;

import org.example.entities.ClientEntity;
import org.example.entities.OrderEntity;
import org.example.entities.OrderItemEntity;
import org.example.entities.OrderStatusEntity;
import org.example.entities.ServiceEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        insertRandomClients(10);
        insertRandomServices(5);
        insertRandomOrderStatuses();
        insertRandomOrders(20);
        selectOrderList();
    }

    private static void insertRandomClients(int count) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            ClientEntity client = new ClientEntity();
            client.setFirstName("Ім'я" + (i + 1));
            client.setLastName("Прізвище" + (i + 1));
            client.setPhone("+38 068 47 85 " + (random.nextInt(900) + 100));
            client.setCar_model("Модель" + (i + 1));
            client.setCar_year(2000 + random.nextInt(20));
            session.save(client);
        }

        transaction.commit();
        session.close();
    }

    private static void insertRandomServices(int count) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            ServiceEntity service = new ServiceEntity();
            service.setName("Послуга" + (i + 1));
            service.setPrice((random.nextInt(500) + 100) * 100);  // price in cents
            session.save(service);
        }

        transaction.commit();
        session.close();
    }

    private static void insertRandomOrderStatuses() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String[] statuses = {"Нове замовлення", "В процесі виконання", "Виконано", "Скасовано клієнтом"};
        for (String status : statuses) {
            OrderStatusEntity orderStatus = new OrderStatusEntity();
            orderStatus.setName(status);
            session.save(orderStatus);
        }

        transaction.commit();
        session.close();
    }

    private static void insertRandomOrders(int count) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Random random = new Random();

        List<ClientEntity> clients = session.createQuery("from ClientEntity", ClientEntity.class).getResultList();
        List<ServiceEntity> services = session.createQuery("from ServiceEntity", ServiceEntity.class).getResultList();
        List<OrderStatusEntity> statuses = session.createQuery("from OrderStatusEntity", OrderStatusEntity.class).getResultList();

        for (int i = 0; i < count; i++) {
            OrderEntity order = new OrderEntity();
            order.setOrderDate(new Date());
            order.setClient(clients.get(random.nextInt(clients.size())));
            order.setStatus(statuses.get(random.nextInt(statuses.size())));
            session.save(order);

            int numServices = random.nextInt(services.size()) + 1;
            for (int j = 0; j < numServices; j++) {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrder(order);
                orderItem.setService(services.get(random.nextInt(services.size())));
                session.save(orderItem);
            }
        }

        transaction.commit();
        session.close();
    }

    private static void selectOrderList() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<OrderEntity> results = session.createQuery("from OrderEntity", OrderEntity.class)
                .getResultList();

        for (OrderEntity order : results) {
            System.out.println(order);
        }
        session.close();
    }
}
