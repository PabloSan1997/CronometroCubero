# Client Cubero

This project is a client-side web application built with React, designed to assist with cubing activities. It features a stopwatch/timer, result tracking, and visualization of performance.

## Technologies Used

*   **Frontend Framework**: React
*   **Build Tool**: Vite
*   **Routing**: TanStack Router
*   **Styling**: Sass
*   **Testing**: Vitest, React Testing Library

## Features

*   **Stopwatch/Timer**: For timing cubing solves.
*   **Result Management**: Tracks and displays cubing results.
*   **Result Visualization**: Includes a graph view for analyzing performance over time.
*   **User Authentication**: A login page is present, suggesting user-specific data handling.

## Project Structure

*   `src/components`: Reusable React components (e.g., timer, result tables, navigation).
*   `src/routes`: Defines the application's pages and navigation (Home, Login, Results, Graph Results).
*   `src/styles`: Sass files for styling the application.
*   `src/utils`: Utility functions for calculations and data handling.
*   `src/tests`: Unit and integration tests.

## Getting Started

### Prerequisites

*   Node.js (LTS version recommended)
*   npm or yarn

### Installation

1.  Clone the repository:
    ```bash
    git clone <repository-url>
    cd clientcubero
    ```
2.  Install dependencies:
    ```bash
    npm install
    # or
    yarn install
    ```

### Running the Application

To start the development server:

```bash
npm run dev
# or
yarn dev
```

The application will be available at `http://localhost:3000`.

### Building for Production

To build the application for production:

```bash
npm run build
# or
yarn build
```

This will create a `dist` directory with the production-ready files.

### Running Tests

To run the tests:

```bash
npm run test
# or
yarn test
```