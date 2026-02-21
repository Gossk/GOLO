# GOLO E-commerce Backend

Full-stack e-commerce REST API built with Spring Boot, Java 25, and MySQL.

## 🚀 Features

- ✅ User Authentication & Authorization (JWT)
- ✅ Product Management (CRUD)
- ✅ Category Management (Hierarchical)
- ✅ Shopping Cart
- ✅ Order Processing
- ✅ Payment Integration
- ✅ Review & Rating System
- ✅ Role-based Access Control (Customer, Admin, Seller)
- ✅ Search & Filtering
- ✅ Pagination & Sorting

## 🛠️ Tech Stack

- **Java**: 25
- **Spring Boot**: 3.3.0
- **Spring Security**: JWT Authentication
- **Spring Data JPA**: Database ORM
- **MySQL**: 8.0+
- **Maven**: Build tool
- **Lombok**: Reduce boilerplate code

## 📋 Prerequisites

- JDK 25
- Maven 3.8+
- MySQL 8.0+
- IDE (IntelliJ IDEA recommended)

## ⚙️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/golo-backend.git
cd golo-backend
```

### 2. Configure MySQL Database
Create a MySQL database:
```sql
CREATE DATABASE golo_ecommerce;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/golo_ecommerce
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build the project
```bash
mvn clean install
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080`

## 📚 API Documentation

### Authentication Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |
| GET | `/api/auth/me` | Get current user |

### Product Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/products` | Get all products | Public |
| GET | `/api/products/{id}` | Get product by ID | Public |
| POST | `/api/products` | Create product | Admin/Seller |
| PUT | `/api/products/{id}` | Update product | Admin/Seller |
| DELETE | `/api/products/{id}` | Delete product | Admin |
| GET | `/api/products/search?keyword=` | Search products | Public |
| GET | `/api/products/featured` | Get featured products | Public |
| GET | `/api/products/new-arrivals` | Get new arrivals | Public |
| GET | `/api/products/top-rated` | Get top rated | Public |

### Category Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/categories` | Get all categories | Public |
| GET | `/api/categories/{id}` | Get category by ID | Public |
| POST | `/api/categories` | Create category | Admin |
| PUT | `/api/categories/{id}` | Update category | Admin |
| DELETE | `/api/categories/{id}` | Delete category | Admin |

### Cart Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/cart` | Get user cart | Customer |
| POST | `/api/cart/items` | Add item to cart | Customer |
| PUT | `/api/cart/items/{id}` | Update cart item | Customer |
| DELETE | `/api/cart/items/{id}` | Remove item | Customer |
| DELETE | `/api/cart/clear` | Clear cart | Customer |

### Order Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/orders` | Create order | Customer |
| GET | `/api/orders/{id}` | Get order by ID | Customer/Admin |
| GET | `/api/orders/my-orders` | Get user orders | Customer |
| GET | `/api/orders/all` | Get all orders | Admin |
| PUT | `/api/orders/{id}/status` | Update order status | Admin |
| DELETE | `/api/orders/{id}/cancel` | Cancel order | Customer/Admin |

### Review Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/reviews` | Create review | Customer |
| GET | `/api/reviews/product/{id}` | Get product reviews | Public |
| GET | `/api/reviews/my-reviews` | Get user reviews | Customer |
| PUT | `/api/reviews/{id}` | Update review | Customer |
| DELETE | `/api/reviews/{id}` | Delete review | Customer/Admin |

## 🔐 Authentication

The API uses JWT for authentication. Include the token in the Authorization header:
```
Authorization: Bearer YOUR_JWT_TOKEN
```

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📦 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/golo/ecommerce/
│   │       ├── config/              # Configuration classes
│   │       ├── controller/          # REST Controllers
│   │       ├── dto/                 # Data Transfer Objects
│   │       ├── entity/              # JPA Entities
│   │       ├── repository/          # JPA Repositories
│   │       ├── service/             # Business Logic
│   │       ├── security/            # Security & JWT
│   │       ├── exception/           # Exception Handling
│   │       └── GoloEcommerceApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👤 Author

Miguel Vidal Castro
- GitHub: [@Gossk](https://github.com/Gossk)
- Email: u202314186@upc.edu.pe

## 🙏 Acknowledgments

- Spring Boot Documentation
- JWT.io
- MySQL Documentation