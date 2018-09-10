package poly;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	private static Node combineLikeTerms(Node newPoly) {
		float tempCoeff = 0; 
		Node addedUp = null;
		for (int i = newPoly.term.degree; i >= 0; i--) {
			tempCoeff = 0;
			for(Node ptr = newPoly; ptr != null; ptr = ptr.next) {
				if (ptr.term.degree == i) {
					tempCoeff = tempCoeff + ptr.term.coeff;
				}
			}
			if (tempCoeff != 0) {
				addedUp = new Node(tempCoeff, i, addedUp);
			}
		}
		return addedUp;
	}
	private static Node sortForAdd(Node newPoly) {
		Node prev = null;
        Node front = newPoly;
        Node next = null;
        while (front != null) {
            next = front.next;
            front.next = prev;
            prev = front;
            front = next;
        }
        newPoly = prev;
        return newPoly;
	}
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node newPoly = null;
		Node Sorted = null;
		while (poly1 != null || poly2 != null) {
			if (poly1 != null && poly2 != null) {
				if (poly1.term.degree == poly2.term.degree) {
				newPoly = new Node((poly1.term.coeff + poly2.term.coeff),poly1.term.degree,newPoly);
				poly1 = poly1.next;
				poly2 = poly2.next;
				}
				else if (poly1.term.degree < poly2.term.degree) {
				newPoly = new Node(poly1.term.coeff, poly1.term.degree,newPoly);
				poly1 = poly1.next;
				
			}
				else if (poly1.term.degree > poly2.term.degree) {
				newPoly = new Node(poly2.term.coeff, poly2.term.degree, newPoly);
				poly2 = poly2.next;
				
		}
			} else {
				if (poly1 != null && poly2 == null) {
					newPoly = new Node(poly1.term.coeff, poly1.term.degree, newPoly);
					poly1 = poly1.next;
					}
				else if (poly2 != null && poly1 == null) {
						newPoly = new Node(poly2.term.coeff, poly2.term.degree, newPoly);
						poly2 = poly2.next;
				}
				
		}
			if (newPoly.term.coeff == 0) {
				newPoly = newPoly.next;
			}
		}
		Sorted = sortForAdd(newPoly);
		return Sorted;
	}
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node newPoly = null;
		if (poly1 == null || poly2 == null) {
			return null;
		}
		for (Node A = poly1; A != null; A = A.next) {
			for (Node B = poly2; B!= null; B = B.next) {
				newPoly = new Node(A.term.coeff * B.term.coeff, A.term.degree + B.term.degree, newPoly);
				
			}
			
			}
		//while (front1 != null) {
				//for(newPoly = front1; front1 != null; front1 = front1.next)
			//if (front1.term.degree ==  front2.term.degree) {
				//Sorted = add(front1,front2);
			//}
			//if (front1.term.degree > front2.term.degree) {
				
		//	}
			//}
		return combineLikeTerms(newPoly);
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float sum = 0;
		while (poly != null) {
			sum = (float)(sum + (Math.pow(x,poly.term.degree) * poly.term.coeff));
			poly = poly.next;
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
