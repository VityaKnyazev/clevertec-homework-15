INSERT INTO address(uuid, area, country, city, street, number)
VALUES ('548f8b02-4f28-49af-864b-b50faa1c1438', 'Central', 'USA', 'New York', 'Broadway', 123),
       ('b835d0cf-793d-4ef9-aa2d-59bdd82ff6f0', 'North', 'Canada', 'Toronto', 'King Street', 456),
       ('bfe2092d-1ea8-4420-bf23-568509be03f7', 'South', 'Mexico', 'Mexico City', 'Juarez Avenue', 789),
       ('8d027666-8d81-4814-b279-87a28688cb5e', 'East', 'China', 'Shanghai', 'Nanjing Road', 12),
       ('cf950eea-5a16-4bf0-901f-b324bbf8ab56', 'West', 'Australia', 'Sydney', 'George Street', 345),
       ('77b6a795-ef46-4602-8f3d-295e55ad1164', 'Central', 'Russia', 'Moscow', 'Tverskaya Street', 678),
       ('8ad957d6-9a44-4a6a-8789-0e3638bcb46c', 'North', 'Sweden', 'Stockholm', 'Drottninggatan', 901),
       ('b60ee80b-8163-4c7d-8d45-87ad7908d9b4', 'South', 'Brazil', 'Rio de Janeiro', 'Avenida Atlantica', 234),
       ('3d948e8e-dd3a-4ab6-a421-fcec24711641', 'East', 'Japan', 'Tokyo', 'Shibuya Crossing', 567),
       ('6ea9ae5b-cc24-40f4-b63e-e958829794db', 'West', 'Canada', 'Vancouver', 'Granville Street', 890),
       ('f1a7308b-92bc-4a50-8559-7cb87a7a7436', 'Central', 'USA', 'Los Angeles', 'Hollywood Boulevard', 123),
       ('63efdd52-1e58-4066-bde3-b9507f5499b3', 'South', 'Russia', 'Bratsk', 'Lenina', 58);

INSERT INTO house(uuid, address_id, create_date)
VALUES ('0929797a-e440-499e-905e-fa5e71bbe6de', 1, '2023-01-01'),
       ('452053c7-f1f9-431b-a5bc-9254a93f7a59', 2, '2023-01-02'),
       ('e14bdfad-2acd-4dbe-955b-8fddcbbcf388', 3, '2023-01-03'),
       ('204647f0-caf6-45be-9512-acac4c628366', 4, '2023-01-04'),
       ('70847027-c60f-4fb9-a65f-73cb657893b9', 5, '2023-01-05'),
       ('46d2e812-80cd-4485-9fec-b20496c24963', 6, '2023-01-06'),
       ('2005eeed-ced9-4408-b3ea-87d7358f78d1', 7, '2023-01-07'),
       ('9850dd00-895e-452b-9b0e-d3ade6680d77', 8, '2023-01-08'),
       ('b094669d-c9ff-4166-aca9-fc85fd89e6fe', 9, '2023-01-09'),
       ('4b736895-8c28-438c-b577-21b4479474ed', 10, '2023-01-10');

INSERT INTO passport(uuid, passport_series, passport_number, create_date)
VALUES ('9478e918-f6c5-4f46-b4aa-cbeb11eb714b', 'KB', '1234568', '2023-07-01'),
       ('8cd97277-2e0a-4eba-ab22-83c3d43ec1de', 'CD', '7890124', '2023-07-02'),
       ('760d95fe-66b4-4b38-abc3-b8c2294a2db3', 'EF', '3456782', '2023-07-03'),
       ('b157d4ca-c8b3-4326-9c1b-9e594b6a3c5e', 'GH', '9012341', '2023-07-04'),
       ('a440805d-d1ed-4604-b6ad-1abf67c14ef9', 'IJ', '5678903', '2023-07-05'),
       ('4d184958-7a7f-4efa-adf2-1529b0c4b39c', 'KL', '1234575', '2023-07-06'),
       ('bb7fa7ee-e31b-4f9d-b9c5-684bdec38954', 'MN', '8901234', '2023-07-07'),
       ('a3c11be9-e6ed-4b3a-beec-a6d833d42857', 'OP', '4567896', '2023-07-08'),
       ('33aa9109-cdd9-45a3-a374-66fcbfa082e1', 'QR', '0123458', '2023-07-09'),
       ('2d7a7989-838f-439e-84ab-8058a06f77e2', 'ST', '6789014', '2023-07-10');

INSERT INTO person(uuid, name, surname, sex, passport_id, house_id)
VALUES ('e45a120c-5c08-4715-bab5-740fc0cad9f5', 'John', 'Doe', 'male', 1, 1),
       ('285b3607-22be-47b0-8bbc-f1f20ee0c17b', 'Jane', 'Smith', 'female', 2, 2),
       ('45638d63-20b5-4335-9bc6-f678ad578dac', 'Bob', 'Johnson', 'male', 3, 3),
       ('c891fba1-ae84-4d1e-96b9-61616cf6b020', 'Alice', 'Williams', 'female', 4, 4),
       ('d285046e-68f7-46b1-8bf9-d0c6e5b22148', 'David', 'Brown', 'male', 5, 5);

INSERT INTO persons_houses_possessing(person_id, house_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 1),
       (2, 2),
       (3, 2),
       (4, 3),
       (5, 4),
       (5, 5);
