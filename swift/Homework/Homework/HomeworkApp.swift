//
//  HomeworkApp.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import SwiftUI

@main
struct HomeworkApp: App {

    @StateObject private var modelData = DataLoader()

    @State private var artworks =  [Artwork]()

    var body: some Scene {
        WindowGroup {
            VStack {
                if artworks.isEmpty {
                    ProgressView()
                    Button("Load artworks") {
                        Task {
                            await load()
                        }
                    }
                } else {
                    ContentView(artworks: artworks)
                }
            }
        }
    }

    func load() async {
        let aws = await DataLoader().loadArtworks()
        artworks.removeAll()
        artworks.append(contentsOf: aws)
    }
}
