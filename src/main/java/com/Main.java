package com;

import com.dao.*;
import com.entity.*;
import com.util.HibernateUtil;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Main {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
      Customer customer = main.createCustomer(1, "Akron", "Timoshenko str, 38", "+375291776896",
                "asasasasas", "test@gmail.com", "Mikhail", "Apalinskiy", true);
      main.customerReturn();
      main.customerRentInventory(1, BigDecimal.valueOf(33.77), 8);
        Film film = main.newFilmWasMade(3, 1, 6, Rating.R, new HashSet<>(Set.of(Feature.BEHIND_THE_SCENES, Feature.COMMENTARIES)),
                (short) 123, BigDecimal.valueOf(11, 32), 1, 10, BigDecimal.valueOf(66.33),
                "new r-18 film", "ABC", (byte) 55);
    }

    private Customer createCustomer(Integer storeId, String name, String address, String phone, String district,
                                    String email, String firstName, String lastName, Boolean active) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            Store store = storeDAO.getById(storeId);
            City byName = cityDAO.getByName(name);
            Address adr = new Address();
            adr.setAddress(address);
            adr.setPhone(phone);
            adr.setCityId(byName);
            adr.setDistrict(district);
            addressDAO.save(adr);
            Customer customer = new Customer();
            customer.setAddressId(adr);
            customer.setActive(active);
            customer.setStoreId(store);
            customer.setEmail(email);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customerDAO.save(customer);
            tx.commit();
            return customer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
            return null;
        }
    }

    private void customerReturn() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setRentalDate(LocalDateTime.now());
            rentalDAO.save(rental);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        }
    }

    public void customerRentInventory(Integer storeId, BigDecimal amount, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = storeDAO.getById(storeId);
            Customer customer = customerDAO.getById(id);
            Inventory inventory = new Inventory();
            inventory.setFilmId(film);
            inventory.setStoreId(store);
            inventoryDAO.save(inventory);
            Staff staff = store.getManagerStaffId();
            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomerId(customer);
            rental.setInventoryId(inventory);
            rental.setStaffId(staff);
            rentalDAO.save(rental);
            Payment payment = new Payment();
            payment.setRentalId(rental);
            payment.setCustomerId(customer);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setAmount(amount);
            payment.setStaffId(staff);
            paymentDAO.save(payment);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
        }
    }

    public Film newFilmWasMade(Integer idLanguage, Integer offset, Integer limit,
                               Rating rating, Set<Feature> features, Short filmLength,
                               BigDecimal replacementCost, Integer actorOff, Integer actorLim,
                               BigDecimal rentalRate, String descr, String title, Byte duration) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Language language = languageDAO.getById(idLanguage);
            List<Category> categories = categoryDAO.getItems(offset, limit);
            List<Actor> actors = actorDAO.getItems(actorOff, actorLim);
            Film film = new Film();
            film.setLanguage(language);
            film.setRating(rating);
            film.setCategories(new HashSet<>(categories));
            film.setSpecialFeatures(features);
            film.setLength(filmLength);
            film.setReplacementCost(replacementCost);
            film.setActors(new HashSet<>(actors));
            film.setRentalRate(rentalRate);
            film.setDescription(descr);
            film.setReleaseYear(Year.now());
            film.setTitle(title);
            film.setRentalDuration(duration);
            filmDAO.save(film);
            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setTitle(title);
            filmText.setDescription(descr);
            filmText.setFilmId(film.getFilmId());
            filmTextDAO.save(filmText);
            transaction.commit();
            return film;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
            return null;
        }
    }
}
