/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.lecture03.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author lcabraja
 */
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int number;
    private final String issuer;

    public Index(int number, String issuer) {
        Objects.requireNonNull(issuer);
        this.number = number;
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }

    @Override
    public String toString() {
        return "Index{" + "number=" + number + ", issuer=" + issuer + '}';
    }

}
