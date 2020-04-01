
/*drop table if exists student_role;
drop table if exists consultant_role;
drop table if exists roles;
drop table if exists thesis;
drop table if exists student;
drop table if exists step;
drop table if exists consultation;
drop table if exists topic;
drop table if exists consultant;




create table if not exists consultant(
                                         consultantid int primary key auto_increment not null,
                                         username varchar(100),
                                         name varchar(100),
                                         email varchar(100),
                                         password varchar(255)
);

create table if not exists topic(
                                    topicid int not null primary key auto_increment,
                                    consultantid int not null,
                                    name varchar(256),
                                    description varchar(2048),
                                    status varchar(32),
                                    foreign key(consultantid) references consultant(consultantid)
);

create table if not exists consultation(
                                           consultationid int not null primary key auto_increment,
                                           topicid int not null,
                                           name varchar(256),
                                           description varchar(2048),
                                           status varchar(32) not null,
                                           time date not null,
                                           foreign key(topicid) references topic(topicid)
);

create table if not exists step(
                                   stepid int not null primary key auto_increment,
                                   topicid int not null,
                                   name varchar(256),
                                   description varchar(512),
                                   deadline date not null,
                                   percentage int not null,
                                   stepstatus int not null,
                                   foreign key(topicid) references topic(topicid)
);


create table if not exists student(
                                      studentid int primary key auto_increment not null,
                                      username varchar(100),
                                      name varchar(100),
                                      email varchar(100),
                                      password varchar(255),
                                      topicid int,
                                      faculty varchar(128),
                                      specialisation varchar(128),
                                      foreign key(topicid) references topic(topicid)
);

create table if not exists thesis(
                                     thesisid int not null primary key auto_increment,
                                     studentid int not null,
                                     name varchar(256),
                                     description varchar(512),
                                     file blob,
                                     foreign key(studentid) references student(studentid)
);

create table if not exists roles(
                                    roleid int primary key auto_increment not null,
                                    name varchar(100)
);

create table if not exists consultant_role(
                                              consultantroleid int not null primary key auto_increment,
                                              consultant_id int not null,
                                              role_id int not null,
                                              foreign key(consultant_id) references consultant(consultantid),
                                              foreign key(role_id) references roles(roleid)
);

create table if not exists student_role(
                                           studentroleid int not null primary key auto_increment,
                                           student_id int not null,
                                           role_id int not null,
                                           foreign key(student_id) references student(studentid),
                                           foreign key(role_id) references roles(roleid)
);

insert ignore into roles (roleid, name) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_CONSULTANT'),
(3, 'ROLE_STUDENT');*/