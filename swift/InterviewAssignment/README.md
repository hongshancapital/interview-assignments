# SwiftUI Demo

## Development Environment
* Xcode 13.1 
* iOS 15.0
* Simulator iPhone 13

## Demo Introduction
The demo trying to call a fake endpoint by using `URLSession` with `Combine`, but I do not use its data instead of loading locally data from `sample.json` file and ignore all errors of HTTP response.
* `ContentView.swift` is the main view.
* `ToDoListViewModel` is the view model of `ContentView.swift`.
* `ToDoListAPI.swift` is used to handle Http requests.

## Author
张世雄(Vic Zhang)
zsx_wust@126.com

## Screenshots
![main screen](screenshots/MainScreen.png)
(main screen)

![adding new item](screenshots/Add-New-Item.png)
(Adding a new item)

![add new item and finish one item](screenshots/after-new-item-added-and-finish-one-item.png)
(add two items *Test* and *Test11111* and then finish *Test*)

![editing](screenshots/editing.png)
(Editing *Working with UI Controls* item)

![after deleted](screenshots/after-deleted.png)
(*Composing Complex Interfaces* and *Working with UI Controls* are deleted)

![searching](screenshots/search.png)
(Searching)