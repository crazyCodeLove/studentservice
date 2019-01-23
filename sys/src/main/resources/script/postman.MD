
## 文档说明

#### 请求方式
- POST application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/student/1`**

- 请求参数:
```json
{
    "param": {
        "id": "12",
        "name": " a",
        "fruits": [
            {
                "color": "red",
                "price": "15"
            },
            {
                "color": "freen",
                "price": "14"
            },
            {
                "color": "yello",
                "price": "41"
            }
        ]
    }
}
```
### 添加用户
#### 请求方式
- POST application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/user`**

- 请求参数:
```json
{
    "param": {
    	"username":"nice",
    	"password":"123456",
    	"email":"hgaha@xx.com",
    	"birthday":"2018-3-2 2:1:2.0"
    }
}
```

### 批量添加用户
#### 请求方式
- POST application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/user/batch`**

- 请求参数:
```json
{
    "param": [
        {
            "username": "nike1",
            "password": "123456",
            "email": "hgaha@xx.com",
            "birthday": "2018-3-2 2:1:2.0"
        },
        {
            "username": "nike2",
            "password": "123456",
            "email": "hgaha@xx.com",
            "birthday": "2018-3-2 2:1:2.0"
        },
        {
            "username": "nike3",
            "password": "123456",
            "email": "hgaha@xx.com",
            "birthday": "2018-3-2 2:1:2.0"
        },
        {
            "username": "nike4",
            "password": "654321",
            "email": "124578@xx.com",
            "birthday": "2018-2-3 2:1:2.0"
        },
        {
            "username": "nike4",
            "password": "654321",
            "email": "222222@xx.com",
            "birthday": "2018-2-3 2:1:2.0"
        }
    ]
}
```

### 修改用户信息
#### 请求方式
- PUT application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/user`**

- 请求参数:
```json
{
    "param": {
    	"uid":"1",
    	"password":"654321",
    	"email":"152485@yy.com",
    	"birthday":"2018-3-2 2:1:2.0"
    }
}
```

### 查询用户列表
#### 请求方式
- GET application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/user/list`**

- 请求参数:
```json
{
    "param": {
    	"pageSize":"5"
    }
}
```

### 批量删除用户
#### 请求方式
- DELETE application/json

#### 请求URL
- **`127.0.0.1:8060/student-service-provider/user`**

- 请求参数:
```json
{
    "param": {
    	"uids":["1","2"]
    }
}
```

