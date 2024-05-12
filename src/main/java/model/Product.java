package model;



public class Product {
    private int  id, price;
    private String name, image, description;
    Category category = new Category();
     int cat_id = category.getId();
    public Product() {
    }

    public Product( int cat_id, int price, String name, String image, String description) {

        this.cat_id = cat_id;
        this.price = price;
        this.name = name;
        this.image = image;
        this.description = description;
    }
    public Product(int id, int category_id, int price, String name, String image, String description) {
        this.id = id;
        this.cat_id = category_id;
        this.price = price;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }



    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", cat_id=" + cat_id +
                '}';
    }


}
