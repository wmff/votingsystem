# REST API for voting system (graduation project)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2da6c3531a084fee82801b99ffd7598c)](https://app.codacy.com/app/wmff/votingsystem?utm_source=github.com&utm_medium=referral&utm_content=wmff/votingsystem&utm_campaign=Badge_Grade_Dashboard)

## The task is:
Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and **README.md with API documentation and curl commands to get data for voting and vote.**

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

## How to run
Set system variable `VOTINGSYSTEM_ROOT` value project location root directory.
example for linux:
```
export VOTINGSYSTEM_ROOT="~/votingsystem"
``` 
and run:
```
mvn clean package cargo:run -P hsqldb
```

## How to use
Example curl commands:
### Administrator manage users
#### Get all users:
```
curl http://localhost:8080/votingsystem/rest/admin/users --user admin@gmail.com:admin
```

#### Get user by id:
```
curl http://localhost:8080/votingsystem/rest/admin/users/100000 --user admin@gmail.com:admin
```

#### Create user:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X POST -d '{"name": "New User","email": "email@mail.com","password": "password","roles": ["ROLE_USER"]}' http://localhost:8080/votingsystem/rest/admin/users --user admin@gmail.com:admin
```

#### Delete user by id:
```
curl -X DELETE http://localhost:8080/votingsystem/rest/admin/users/100014 --user admin@gmail.com:admin
```

#### Update user by id:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X PUT -d '{"name": "New User","email": "email@mail.com","password": "password","roles": ["ROLE_USER"]}' http://localhost:8080/votingsystem/rest/admin/users/100000 --user admin@gmail.com:admin
```

#### Get user by email:
```
curl http://localhost:8080/votingsystem/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin
```

### Administrator manage restaurants
#### Get all restaurants:
```
curl http://localhost:8080/votingsystem/rest/admin/restaurants --user admin@gmail.com:admin
```

#### Get restaurant by id:
```
curl http://localhost:8080/votingsystem/rest/admin/restaurants/100002 --user admin@gmail.com:admin
```

#### Get restaurant by id with dishes:
```
curl http://localhost:8080/votingsystem/rest/admin/restaurants/100003/withDishes --user admin@gmail.com:admin
```

#### Create restaurant:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X POST -d '{"name": "New Restaurant"}' http://localhost:8080/votingsystem/rest/admin/restaurants --user admin@gmail.com:admin
```

#### Delete restaurant by id:
```
curl -X DELETE http://localhost:8080/votingsystem/rest/admin/restaurants/100003 --user admin@gmail.com:admin
```

#### Update restaurant by id:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X PUT -d '{"name": "Updated Restaurant"}' http://localhost:8080/votingsystem/rest/admin/restaurants/100002 --user admin@gmail.com:admin
```

### Administrator manage dishes
#### Get all dishes by restaurant and current date:
```
curl http://localhost:8080/votingsystem/rest/admin/restaurants/100003/dishes --user admin@gmail.com:admin
```

#### Get all dishes by restaurant and by date:
```
curl http://localhost:8080/votingsystem/rest/admin/restaurants/100002/dishes?date=2019-03-21 --user admin@gmail.com:admin
```

#### Get dishes by restaurant and between date:
```
curl "http://localhost:8080/votingsystem/rest/admin/restaurants/100002/dishes/filter?startDate=2019-03-21&endDate=2019-03-22" --user admin@gmail.com:admin
```

#### Get dish by restaurant and dish id:
```
curl "http://localhost:8080/votingsystem/rest/admin/restaurants/100002/dishes/100005" --user admin@gmail.com:admin
```

#### Create dish:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X POST -d '{"name": "New Dish", "price": "123"}' http://localhost:8080/votingsystem/rest/admin/restaurants/100003/dishes --user admin@gmail.com:admin
```

#### Delete dish by id:
```
curl -X DELETE http://localhost:8080/votingsystem/rest/admin/restaurants/100003/dishes/100006 --user admin@gmail.com:admin
```

#### Update dish by id:
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X PUT -d '{"name": "Updated Dish", "price": "123"}' http://localhost:8080/votingsystem/rest/admin/restaurants/100003/dishes/100012 --user admin@gmail.com:admin
```

### User manage profile
#### Get user profile
```
curl http://localhost:8080/votingsystem/rest/profile --user user@yandex.ru:password
```

#### Delete user profile
```
curl -X DELETE http://localhost:8080/votingsystem/rest/profile --user user@yandex.ru:password
```

#### Register user
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X POST -d '{"name": "New User","email": "email@email.com","password": "password"}' http://localhost:8080/votingsystem/rest/profile/register
```

#### Update profile
```
curl -H 'Content-Type:application/json;charset=UTF-8' -X PUT -d '{"name": "Updated User","email": "updated@email.com","password": "password"}' http://localhost:8080/votingsystem/rest/profile --user user@yandex.ru:password
```

### User get restaurants
#### User get all restaurants with today dishes 
```
curl http://localhost:8080/votingsystem/rest/restaurants
```

#### User get restaurant by name
```
curl http://localhost:8080/votingsystem/rest/restaurants/by?name="restaurant%202" --user user@yandex.ru:password
```

### Voting
```
curl http://localhost:8080/votingsystem/rest/vote?restaurantId=100003 --user admin@gmail.com:admin
```



