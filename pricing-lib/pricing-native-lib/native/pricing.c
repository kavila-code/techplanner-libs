#include <stdio.h>

/**
 * calculate_discount
 * Calcula el valor del descuento.
 */
double calculate_discount(double amount, double discountPercentage) {
    return amount * (discountPercentage / 100.0);
}

/**
 * calculate_tax
 * Calcula el valor del impuesto.
 */
double calculate_tax(double subtotal, double taxPercentage) {
    return subtotal * (taxPercentage / 100.0);
}

/**
 * calculate_final_price
 * Calcula el precio final:
 * (amount - discount) + tax
 */
double calculate_final_price(
    double amount,
    double discountPercentage,
    double taxPercentage
) {

    double discount =
        calculate_discount(amount, discountPercentage);

    double subtotal = amount - discount;

    double tax =
        calculate_tax(subtotal, taxPercentage);

    return subtotal + tax;
}