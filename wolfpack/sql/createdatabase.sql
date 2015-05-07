CREATE DATABASE wolfpack CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE wolfpack_test CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'wolfpack_u'@'%' IDENTIFIED by 'wolfpack_p';
GRANT ALL PRIVILEGES ON wolfpack.* TO 'wolfpack_u'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON wolfpack_test.* TO 'wolfpack_u'@'%' WITH GRANT OPTION;
CREATE USER 'wolfpack_u'@'localhost' IDENTIFIED by 'wolfpack_p';
GRANT ALL PRIVILEGES ON wolfpack.* TO 'wolfpack_u'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON wolfpack_test.* TO 'wolfpack_u'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;