package com.example.saumilpatel.myadowner;

/**
 * Created by Saumil Patel on 14-03-17.
 */
public class Card {
    private String News;
    private String Edition;
    private String Date;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String composetext;
    private String fontcolor;
    private String backgroundcolor;
    private String imageheight;
    private String imagewidth;
    private String payment;
    private String special_request;
    private String Category;
    private  String maincategory;
    private String confirm;
    private String order_id;
    private String confirmtext;


    public Card(String Category, String maincategory, String News, String Edition, String Date, String firstname, String lastname, String email, String phone, String composetext, String fontcolor, String backgroundcolor, String imageheight, String imagewidth, String payment, String special_request, String confirm, String order_id,String confirmtext)
    {
        this.Category=Category;
        this.News =News;
        this.Edition = Edition;
        this.Date = Date;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.phone=phone;
        this.composetext=composetext;
        this.fontcolor=fontcolor;
        this.backgroundcolor=backgroundcolor;
        this.imageheight=imageheight;
        this.imagewidth=imagewidth;
        this.payment=payment;
        this.special_request=special_request;
        this.maincategory=maincategory;
        this.confirm=confirm;
        this.order_id=order_id;
        this.confirmtext=confirmtext;


    }
    public String getMaincategory() {
        return maincategory;
    }

    public String getCategory() {
        return Category;
    }

    public String getNews() {
        return News;
    }

    public String getConfirmtext() {
        return confirmtext;
    }

    public String getDate() {
        return Date;
    }

    public String getEdition() {
        return Edition;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getComposetext() {
        return composetext;
    }

    public String getFontcolor() {
        return fontcolor;
    }

    public String getConfirm() {
        return confirm;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public String getImagewidth() {
        return imagewidth;
    }

    public String getImageheight() {
        return imageheight;
    }

    public String getPayment() {
        return payment;
    }

    public String getSpecial_request() {
        return special_request;
    }
}