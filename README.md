# JavUno

JavUno is a Java-based implementation of the popular card game Uno. This project allows you to play Uno with up to 4 players, including bots to fill in the remaining players if needed.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Game Rules](#game-rules)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/Uno_Game.git
    cd Uno_Game\src\main\java
    ```

## Usage

To start the game:
```sh
javac UnoApp.java
java UnoApp
```

## Game Rules
Follow the on-screen instructions to play the game.

### Special Cards

- **Skip**: The next player is skipped.
- **Reverse**: Reverses the direction of play.
- **Draw Two**: The next player draws two cards and loses their turn.
- **Wild**: The player declares the next color to be matched.
- **Wild Draw Four**: The player declares the next color to be matched and the next player draws four cards and loses their turn.

## Project Structure

```
├── .gitignore
├── .idea/
├── .vscode/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── App/
│   │           ├── CardModel/
│   │           ├── Deck/
│   │           ├── Game/
│   │           ├── Game/Screens/
│   │           ├── OurUtils/
│   │           └── PlayerModel/
│   └── test/
│       └── java/
│           └── AppTools/
│               └── Deck/
└── target/
```

### Key Classes

- **UnoApp**: The main class to start the game.
- **GameService**: Manages the game flow.
- **Deck**: Represents the deck of cards.
- **Player**: Abstract class for players.
- **HumanPlayer**: Represents a human player.
- **BotPlayer**: Represents a bot player.
- **AbstractCard**: Abstract class for cards.
- **NumberedCard**, **ActionCard**, **WildCard**: Different types of cards.
- **Utils**: Utility functions.
- **Screens**: Classes for different game screens (e.g., WelcomeScreen, HowToPlayScreen).
