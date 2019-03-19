INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(2, 'John', 'Doe', 'john.doe@gmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(3, 'Ashlynn', 'Sanford', 'Ike.Ward49@yahoo.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(4, 'Nico', 'Gorczany','Shanny39@yahoo.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(5, 'Heather', 'Quigley', 'Rusty_Shields42@yahoo.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(6, 'Greta', 'Stehr', 'Lyla_Block29@example.org', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(7, 'Leonor', 'Gottlieb', 'Margaret_Klein@example.net', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(8, 'Gail', 'Orn', 'Rubye67@yahoo.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(9, 'Nels', 'Goldner', 'Lempi_Sauer@hotmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(10, 'Isabelle', 'Grady', 'Cora45@hotmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(11, 'Abigale', 'Rutherford', 'Charlie91@gmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(12, 'Tyson', 'Candido', 'Hal.Weimann@gmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(13, 'Emile', 'Kris', 'Modesta.Christiansen84@hotmail.com', '2017-08-28', '');
INSERT INTO clients (id, name, lastname, email, create_at, photo) VALUES(14, 'Berry', 'Zemlak', 'Bailee.Shields@hotmail.com', '2017-08-28', '');

INSERT INTO products(name, cost, create_at) VALUES('Sin marca Acero Bike', '810.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Increíble Fresco Pantalones', '350.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Hecho a mano Madera Pantalones', '200.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Artesanal Hormigón Presidente', '240.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Inteligente Metal Bike', '55.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Increíble Cotton Tocino', '776.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Genérica Cotton Ensalada', '133.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Sin marca Cotton Pescado', '136.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Práctica Fresco Camisa', '385.00', '2019-03-20');
INSERT INTO products(name, cost, create_at) VALUES('Sabrosa Cotton Toallas', '679.00', '2019-03-20');


/* some invoices */
INSERT INTO invoices(description, remark, client_id, create_at) VALUES('Factura Acero Guantes', 'withdrawal Indian Rupee Ngultrum', 1, NOW());
INSERT INTO items_invoice(quantity, invoice_id, product_id) VALUES(1,1,1);
INSERT INTO items_invoice(quantity, invoice_id, product_id) VALUES(2,1,4);
INSERT INTO items_invoice(quantity, invoice_id, product_id) VALUES(1,1,5);
INSERT INTO items_invoice(quantity, invoice_id, product_id) VALUES(1,1,7);

INSERT INTO invoices(description, remark, client_id, create_at) VALUES('Factura Pequeño Madera Tocino', 'bypassing virtual', 1, NOW());
INSERT INTO items_invoice(quantity, invoice_id, product_id) VALUES(3, 2, 6);
