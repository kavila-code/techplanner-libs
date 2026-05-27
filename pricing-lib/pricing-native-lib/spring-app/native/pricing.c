#include "pricing.h"

/*
 * calculate_price
 * basePrice + porcentaje de impuesto
 */
double calculate_price(double basePrice, double tax) {
    return basePrice + (basePrice * tax / 100.0);
}

/*
 * apply_discount
 * aplica descuento porcentual
 */
int apply_discount(int amount, int percentage) {
    return amount - (amount * percentage / 100);
}

/*
 * fibonacci recursivo
 */
int fibonacci(int n) {

    if (n <= 1) {
        return n;
    }

    return fibonacci(n - 1) + fibonacci(n - 2);
}