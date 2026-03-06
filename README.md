# 🛒 GOLO E-Commerce Backend

Backend REST API construido con **Spring Boot 3.2 + Spring Security + JWT + MySQL**.

---

## ⚙️ Requisitos Previos

- **Java 21** (JDK)
- **MySQL 8.0+**
- **IntelliJ IDEA**
- **Maven**

---

## 📡 Endpoints Principales

### Auth
```
POST /api/auth/login      → Login
POST /api/auth/register   → Registro
GET  /api/auth/me         → Usuario actual (requiere token)
```

### Productos (pantalla principal)
```
GET /api/products                       → Todos (paginado)
GET /api/products/featured              → Destacados (banner)
GET /api/products/new-arrivals          → Nuevos
GET /api/products/top-rated             → Mejor calificados
GET /api/products/search?keyword=iphone → Buscar
GET /api/products/category/{id}         → Por categoría
```

---

## 🏗️ Próximos módulos a implementar

- [ ] Cart (Carrito de compras)
- [ ] Orders (Pedidos)
- [ ] Reviews (Calificaciones)
- [ ] Payments (Pagos)
- [ ] User profile management