# Laboratoire 8: Echecs
Durée du laboratoire: 12 périodes. A rendre pour le 7 janvier 2021 au début de la séance de laboratoire.
## 1. Objectifs
Le but de ce laboratoire est d’implémenter un jeu d’échecs fonctionnel. Une interface graphique ainsi qu’un
mode console vous sont fournis. Les règles à implémenter sont les [suivantes](https://en.wikipedia.org/wiki/Rules_of_chess) :
- Les mouvements et les prises de toutes les pièces (pions, tours, cavaliers, fous, dames, rois).
- Le petit et le grand roque doivent être fonctionnels. Leur mouvement est initié en bougeant le roi de deux
cases vers la droite ou vers la gauche. Ce coup ne peut être effectué si le roi est en échec, s’il a déjà bougé,
si la tour concernée a déjà bougé ou si une des cases sur lesquelles le roi passe est en échec.
- La prise en passant doit être fonctionnelle. Ce coup s’effectue en prenant un pion ayant avancé de deux
cases au tour précédent comme s’il n’avait avancé que d’une case.
- La promotion de pions doit être implémentée. Les types de promotions possibles sont tour, cavalier, fou et
dame.
- Lorsqu’un roi est mis en échec, le message “Check !” doit être affiché sur la vue (en utilisant sa méthode
``displayMessage(String))``.
Points bonus :
- Implémentation de la détection de l’échec et mat et des des match nuls par pat ou impossibilité de mater.

## Implémentation
Les classes et interfaces suivantes sont fournies et ne doivent pas être modifiées :
- *PieceType* est un enum listant les différents types de pièces.
- *PlayerColor* est un enum listant les couleurs des joueurs (blanc, noir)
- *ChessView* est une interface permettant de représenter une vue. Deux implémentations en sont fournies : la
vue GUI *GUIView* et la vue console *ConsoleView*. Tout le code de ces vues se trouve dans les packages
views et assets.
- *ChessController* est une interface permettant de contrôler le jeu d’échecs depuis la vue. Il s’agit de
l’**interface à implémenter dans le contrôleur**.
Il est conseillé de mettre toutes les classes nécessaires au contôleur dans un package engine. Pour utiliser le
code fourni, le main doit être semblable à :
```java
public static void main(String[] args) {
 // 1. Création du contrôleur pour gérer le jeu d’échec
 ChessController controller = new ...; // Instancier un ChessController
 // 2. Création de la vue
 ChessView view = new GUIView(controller); // mode GUI
 // = new ConsoleView(controller); // mode Console
 // 3 . Lancement du programme.
 controller.start(view);
}
```

## Diagramme de classes

![Diagramme](https://user-images.githubusercontent.com/6887819/190913166-c4d9735e-9e3e-468d-98f2-0a31336c7f1c.jpg)
