A simple Jakarta EE application for library management using Servlet, JPA, JSP, TomEE, and MySQL. The application is developed using the IntelliJ IDEA IDE.
Before running, the MySQL database must be seeded and connected using the included file, and the TomEE container must be set up.

Features:
*Reader*
- Can view their borrowings (title, when borrowed, due date for return).
- Can view their reservations and reserve a new book (reservations last for 3 days).

*Employee*
- Can manage reader accounts (edit, delete, add).
- Can manage the book collection (edit, delete, add books).
- Can view reader borrowings, process book returns, and lend new books (borrowings last for 30 days).
- Can view reader reservations, cancel reservations, and lend reserved books.
