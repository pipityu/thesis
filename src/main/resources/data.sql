

/*
create table if not exists consultant
(
    consultantid int primary key auto_increment not null,
    username     varchar(100),
    name         varchar(100),
    email        varchar(100),
    password     varchar(255)
);

create table if not exists topic
(
    topicid      int not null primary key auto_increment,
    consultantid int not null,
    name         varchar(256),
    description  varchar(512),
    foreign key (consultantid) references consultant (consultantid)
);

create table if not exists consultation
(
    consultantid int not null primary key auto_increment,
    topicid      int not null,
    name         varchar(256),
    description  varchar(512),
    foreign key (topicid) references topic (topicid)
);

create table if not exists step
(
    stepid      int not null primary key auto_increment,
    topicid     int not null,
    name        varchar(256),
    description varchar(512),
    foreign key (topicid) references topic (topicid)
);

create table if not exists comment
(
    commentid   int not null primary key auto_increment,
    stepid      int not null,
    name        varchar(256),
    description varchar(512),
    foreign key (stepid) references step (stepid)
);

create table if not exists student
(
    studentid      int primary key auto_increment not null,
    username       varchar(100),
    name           varchar(100),
    email          varchar(100),
    password       varchar(255),
    topicid        int,
    faculty        varchar(128),
    specialisation varchar(128),
    foreign key (topicid) references topic (topicid)
);

create table if not exists thesis
(
    thesisid    int not null primary key auto_increment,
    studentid   int not null,
    name        varchar(256),
    description varchar(512),
    file        blob,
    foreign key (studentid) references student (studentid)
);

create table if not exists roles
(
    roleid int primary key auto_increment not null,
    name   varchar(100)
);

create table if not exists consultant_role
(
    consultant_id int not null,
    role_id       int not null,
    foreign key (consultant_id) references consultant (consultantid),
    foreign key (role_id) references roles (roleid)
);

create table if not exists student_role
(
    student_id int not null,
    role_id    int not null,
    foreign key (student_id) references student (studentid),
    foreign key (role_id) references roles (roleid)
);


insert into consultant
values (default, 'YYYCON', 'real name con', 'consultant@consultant.com',
        '$2y$12$szMzkqB3sXInJKWcFgv08eZnpE/FKETgsCHLgUucEPl/6WveVWlMi');

insert into topic
values (default, 1, 'teszt téma1', 'teszt téma1 leírása');

insert into consultation
values (default, 1, 'téma 1 konzultáció 1 név', 'téma 1 konzultáció 1 leírás');
insert into consultation
values (default, 1, 'téma 1 konzultáció 2 név', 'téma 1 konzultáció 2 leírás');

insert into step
values (default, 1, 'lépés1 név', 'lépés1 téma 1hez');
insert into step
values (default, 1, 'lépés2 név', 'lépés2 téma 1hez');

insert into comment
values (default, 1, 'step 1 hez comment1 név', 'step1 hez comment 1 leírás');
insert into comment
values (default, 1, 'step 1 hez comment2 név', 'step1 hez comment 2 leírás');
insert into comment
values (default, 2, 'step 2 hez comment1 név', 'step1 hez comment 1 leírás');

insert into student
values (default, 'XXXUSR', 'real name usr', 'user@user.com',
        '$2a$10$CieQ90INzyCFNykaBz0GWOIQ6ckqVml4CrblMgNTce4cZeGBmiqF.', null, 'fac', 'spec');

insert into thesis
values (default, 1, 'szakdoga 1 név', 'szakdoga 1 leírás', null);

insert into roles (roleid, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_CONSULTANT'),
       (3, 'ROLE_STUDENT');

insert into student_role(student_id, role_id)
values (1, 3);
insert into consultant_role(consultant_id, role_id)
values (1, 2);*/


