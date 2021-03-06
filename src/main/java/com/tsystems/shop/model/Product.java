package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Product entity model. This class maps on products Table in our Database.
 * There we store all information about the product in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {

    /**
     * Product ID. It generates by hibernate while inserting.
     * This filed connects with product_id column in products table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private long id;

    /**
     * Product name.
     * This filed connected with name column in products table.
     * Cannot be nullable and must be unique.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Product price.
     * This filed connected with price column in products table.
     * Cannot be nullable.
     */
    @Column(name = "price", nullable = false)
    private String price;

    /**
     * Product image path.
     * This filed connected with image column in products table.
     * Cannot be nullable.
     */
    @Column(name = "image", nullable = false)
    private String image;

    /**
     * Product status. If true, guests and users cannot see this product(admins can) and vice versa.
     * This filed connected with status column in products table.
     * Cannot be nullable.
     */
    @Column(name = "status", nullable = false)
    private boolean active;

    /**
     * Category object. We had Category class {@link Category} and every product in store
     * should be a part of any category. By this reason we should store reference to a category object.
     * Hibernate allow us to get Size object from OrdersProducts object.
     * This filed connected with category_id column in products table.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Attribute object. We had Attribute class {@link Attribute} and every product in store
     * has it detailed information(for example, color, size, weight, etc.).
     * By this reason was created attributes table. See Attribute class declaration for more details {@link Attribute}.
     * Hibernate allow us to get Attribute object from Product object.
     * This filed connected with attribute_id column in products table.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    private Attribute attributes;

    /**
     * Empty constructor for Hibernate
     */
    public Product() {
    }

    /**
     * Our custom constructor to initialize all necessary fields.
     * @param name - see fields declaration.
     * @param price - see fields declaration.
     * @param image - see fields declaration.
     * @param category - see fields declaration.
     * @param attributes - see fields declaration.
     */
    public Product(String name, String price, String image, Category category, Attribute attributes) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.category = category;
        this.attributes = attributes;
        this.active = true;
    }

    /**
     * Simple getter
     * @return Product ID value
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id is value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return Product name value
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter
     * @param name is value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter
     * @return Product price value
     */
    public String getPrice() {
        return price;
    }

    /**
     * Simple setter
     * @param price is value to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Simple getter
     * @return Product image path value
     */
    public String getImage() {
        return image;
    }

    /**
     * Simple setter
     * @param image is value to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Simple getter
     * @return Product category object
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Simple setter
     * @param category is object to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Simple getter
     * @return Product attribute object
     */
    public Attribute getAttributes() {
        return attributes;
    }

    /**
     * Simple getter
     * @return Product status value
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Simple setter
     * @param active is value to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Simple setter
     * @param attributes is object to set
     */
    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return (int)(id/100);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Product)) return false;

        Product tmp = (Product) obj;
        return tmp.getId() == id;
    }
}