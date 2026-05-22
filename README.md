# Online Sales System

Online Sales System është një aplikacion desktop i zhvilluar me JavaFX dhe MySQL për simulimin e një sistemi interaktiv të shitje-blerjeve online.

## Teknologjitë e përdorura

- Java
- JavaFX
- FXML
- Maven
- MySQL
- JDBC
- CSS

## Funksionalitetet kryesore

- Regjistrimi dhe kyçja e përdoruesve
- Ruajtja e password-it me hash dhe salt
- Shtimi dhe shfaqja e produkteve
- Kërkimi i produkteve
- Produktet e mia
- Soft delete për produkte
- Shporta
- Checkout me transaction
- Historiku i porosive

## Struktura e projektit

Projekti është ndarë në disa shtresa kryesore:

- `controllers` - menaxhojnë ndërfaqen grafike
- `services` - përmbajnë logjikën e aplikacionit
- `repository` - komunikojnë me databazën
- `models` - përfaqësojnë objektet kryesore
- `dto` - përdoren për transferimin e të dhënave
- `migrations` - përmbajnë SQL scripts për krijimin e databazës

## Databaza

Projekti përdor MySQL dhe përmban tabelat:

- `users`
- `products`
- `cart_items`
- `orders`
- `order_items`

## Përshkrim i shkurtër

Ky projekt simulon procesin bazë të shitje-blerjeve online, duke përfshirë regjistrimin e përdoruesve, menaxhimin e produkteve, shportën, checkout-in dhe historikun e porosive.
