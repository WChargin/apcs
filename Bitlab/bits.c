/*
 * bits.c
 * CMU 15-213 Lab 1: Twiddling Bits
 * William Chargin
 * 7 January 2014
 */
#include "bits.h"

/* unless otherwise specified, allowed operators are: */
/* ! ~ & ^ | + << >> */

/* Part I: Bit Manipulations */

/* duplicate bitwise and (&) using only | and ~ */
/* rating: 1; max ops: 8 */
type bitAnd(type x, type y) {
    /* for any bits a, b: a AND b = NOT (NOT a OR NOT b) */
    return ~(~x | ~y);
}

/* duplicate bitwise or (|) using only & and ~ */
/* rating: 1; max ops: 8 */
type bitOr(type x, type y) {
    /* for any bits a, b: a AND b = NOT (NOT a AND NOT b) */
    return ~(~x & ~y);
}

/* returns (x == y ? 1 : 0) */
/* rating: 2; max ops: 5 */
type isEqual(type x, type y) {
    /* for any bits a, b: a XOR b = (a AND NOT b) or (b AND NOT a) */
    /* in other words, if NOT (a XOR b) then (a AND b) OR (NOT a AND NOT b) */
    /* so, if NOT (a XOR b) then a = b */
    /* if x == y then x ^ y == 0 */
    /* in addition, !z is defined as (z ? 0 : 1), so !(x ^ y) = (x == y) */
    return !(x ^ y);
}

/* does a logical right shift of x by n */
/* rating: 3; max ops: 16 */
type logicalShift(type x, type n) {
    /* first do a logical shift */
    type shifted = (x & 0x7FFFFFFF) >> n;
    
    /* if original high bit was set it should now go in the nth-high bit */
    /* do the (possibly incorrect) original shift and extract that bit */
    type mask = (x >> n) & (0x80000000U >> n);
    
    /* add this bit back on if necessary */
    return shifted | mask;
}

/* returns (x contains odd number of 1s ? 1 : 0) */
/* rating: 4; max ops: 20 */
type bitParity(type x) {
    /* XORing the left and right halves of a number quasi-recursively */
    /* explanation: */
    /* 1 XOR 0 = 0 XOR 1 = 1; 1 XOR 1 = 0 XOR 0 = 0 */
    /* thus for bits U, V: bitParity(0bUV) = U XOR V */
    /* also, for any x with left- and right-halves l, r: */
    /* bitParity(x) = bitParity(l) XOR bitParity(r); */
    /* so recursively XOR each side */
    /* assignment requires straightline, so inline for 32-bit integers */
    
    type leftHalf = logicalShift(x, 16); /* counts as six operations */
    type rightHalf = x & 0xFFFF;
    x = leftHalf ^ rightHalf;
    
    /* after the first iteration we don't need to logicalShift anymore */
    /* because x has 16 highest bits 0, so >> is logical shift */
    leftHalf = x >> 8;
    rightHalf = x & 0xFF;
    x = leftHalf ^ rightHalf;
    
    leftHalf = x >> 4;
    rightHalf = x & 0xF;
    x = leftHalf ^ rightHalf;
    
    leftHalf = x >> 2;
    rightHalf = x & 0x3;
    x = leftHalf ^ rightHalf;
    
    leftHalf = x >> 1;
    rightHalf = x & 0x1;
    x = leftHalf ^ rightHalf;
    
    return x;
}

/* returns a mask that marks the position of least significant 1 bit of x */
/* all other positions of the mask should be zero */
/* rating: 4; max ops: 30 */
type leastBitPos(type x) {
    /* exploit the builtin addition operator */
    /* binary addition on x is essentially "fill x with zeroes until you */
    /* find a zero, then replace that with a one" */
    /* so binary addition on ~x will result in the only bit in common be the */
    /* LSB, and then simple bitwise and (&) will create the mask */
    return x & (~x + 1);
}

/* compute !x without using the ! operator */
/* rating: 4; max ops: 12 */
type bang(type x) {
    /* bang(x) = (x != 0 ? 1 : 0); */
    /* Iff (x == 0) then (x == -x) */
    /* Iff (x != 0) then (x != -x) */
    /* Taking advantage of signs: sgn(x) == sgn(-x) iff x == 0 iff bang(x) */
    
    int negative_x = ~x + 1;
    
    /* 0 for nonnegative, 1 for negative */
    int sgn_pos_x = (     x     >> 31) & 1;
    int sgn_neg_x = (negative_x >> 31) & 1;
    
    /* we want return(sgn_pos_x == sgn_neg_x); but == requires bang to work */
    int expr = ~(sgn_pos_x | sgn_neg_x);
    
    /* if x == 0 then sgn_pos_x = sgn_neg_x = 0, so expr will be 0b1111...  */
    /* if x != 0 then expr will cancel out entirely and be zero             */
    /* therefore we can just extract the last bit for the answer            */
    return expr & 1;
}

#ifdef __METHODS_PAST_HERE_NOT_YET_IMPLEMENTED__

/* returns a count of the number of 1s in the argument */
/* rating: 4; max ops: 40 */
type bitCount(type x) {
    
}

/* compute !x without using the ! operator */
/* rating: 4; max ops: 12 */
type bang(type x) {
    
}

/* Part II: Two's Complement Arithmetic */

/* returns the largest integer */
/* rating: 1; max ops: 4 */
type tmax() {
    
}

/* compute -x without using - */
/* rating: 2; max ops: 5 */
type negate(type x) {
    
}

/* determines whether y can be added to x without overflow */
/* rating: 3; max ops: 20 */
type addOk(type x, type y) {
    
}

/* returns !!x without using ! */
/* rating: 4; max ops: 10 */
bool isNonZero(type x) {
    
}

/* converts sign-magnitude to two's-complement */
/* high-order bit of input is sign bit, remainder magnitude */
/* return two's-complement representation */
/* rating: 4; max ops: 15 */
type sm2tc(type x) {
    
}

/* compute x == tmin ? tmin : abs(x) */
/* rating: 4; max ops: 10 */
type mathabs(type x) {
    
}

/* returns (x + y overflows ? tmax or tmin : x + y) */
/* rating: 4; max ops: 30 */
type satAdd(type x, type y) {
    
}

#endif
