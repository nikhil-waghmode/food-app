# Food App

A user-friendly food ordering application designed to streamline the process of browsing, selecting, and ordering meals from various restaurants. This project emphasizes an intuitive user interface and efficient backend operations to enhance the food ordering experience.

---

## Features

- **Welcome Page**: Displays the app's logo and name, providing a warm introduction to users.
- **User Authentication**: Secure login and registration system for users to access personalized features.
- **Restaurant Listings**: Browse through a curated list of restaurants with detailed menus.
- **Search Functionality**: Easily search for favorite dishes or restaurants.
- **Cart Management**: Add, remove, and manage items in the shopping cart before checkout.
- **Order Tracking**: Real-time updates on order status from preparation to delivery.

---

## Technologies Used

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java (Spring Boot, Spring Security, JWT Authentication)
- **Database**: MySQL
- **Version Control**: Git
- **Architecture**: REST API + MVC (Model-View-Controller) Pattern
- **Security**: JWT (JSON Web Token) for secure authentication
- **Frameworks**: Spring Boot, Spring Security
- **Build Tool**: Maven

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone --branch food-app-v1 https://github.com/nikhil-waghmode/food-app.git
   ```

2. **Navigate to the Project Directory**:
   ```bash
   cd food-app
   ```

3. **Set Up the Backend**:
   - Ensure you have Java and Maven installed.
   - Open the project in your IDE.
   - Run the `FoodAppApplication.java` file to start the Spring Boot backend.

4. **Set Up the Frontend**:
   - Open the `login.html` file in your preferred web browser.

5. **Configure the Database**:
   - Set up a MySQL database.
   - Update your connection settings in `application.properties`.

## Project Structure (REST + MVC Pattern)

```
src/
├── main/
│   ├── java/
│   │   └── com/capgemini/food_app/
│   │       ├── controller/         # REST controllers handling HTTP requests
│   │       ├── service/            # Business logic layer
│   │       ├── repository/         # JPA repositories for DB operations
│   │       ├── model/              # Entity classes mapped to DB tables
│   │       ├── security/           # Security-related classes (JWT, Spring Security)
│   │       └── FoodAppApplication.java  # Main Spring Boot application class
│   └── resources/
│       ├── application.properties  # App configuration
│       └── static/                 # Frontend HTML, CSS, JS files

```

## Entity Details

| **Entity Name** | **Data Types (Spring Boot)** | **Attributes**                                                          |
| --------------- | ---------------------------- | ----------------------------------------------------------------------- |
| **User**        | `Long`                       | `id`                                                                    |
|                 | `String`                     | `name`, `email`, `password`, `phone`, `userType`, `location`, `userImg` |
| **Restaurant**  | `Long`                       | `id`                                                                    |
|                 | `String`                     | `name`, `location`, `contact`, `restaurantImg`                          |
|                 | `Long`                       | `ownerId`                                                               |
| **FoodItem**    | `Long`                       | `id`                                                                    |
|                 | `String`                     | `name`, `category`, `cuisine`, `itemImg`                                |
|                 | `Double`                     | `price`                                                                 |
|                 | `Long`                       | `restaurantId`                                                          |
| **Order**       | `Long`                       | `id`, `userId`, `restaurantId`                                          |
|                 | `LocalDate`                  | `date`                                                                  |
|                 | `Double`                     | `totalAmount`                                                           |
| **OrderItem**   | `Long`                       | `id`, `orderId`, `itemId`                                               |
|                 | `Integer`                    | `quantity`                                                              |
| **Review**      | `Long`                       | `id`, `restaurantId`, `userId`                                          |
|                 | `Float`                      | `rating`                                                                |
|                 | `String`                     | `feedback`                                                              |
|                 | `LocalDate`                  | `date`                                                                  |


## Usage

1. **Launch the Application**:
   - Run `FoodAppApplication.java` in your IDE or terminal.
   - Open `login.html` in your web browser.

2. **Register or Log In**:
   - Use the UI to create an account or log in.

3. **Browse Restaurants and Menus**:
   - Explore restaurants and menu options.

4. **Place an Order**:
   - Add items to cart and proceed to checkout.

5. **Track Your Order**:
   - Monitor real-time order status.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the Repository  
2. Create a New Branch:
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit Your Changes:
   ```bash
   git commit -m "Add your feature"
   ```
4. Push to the Branch:
   ```bash
   git push origin feature/YourFeature
   ```
5. Open a Pull Request

## Contact

For any inquiries or feedback, please reach out to [nick13waghmode@gmail.com].
