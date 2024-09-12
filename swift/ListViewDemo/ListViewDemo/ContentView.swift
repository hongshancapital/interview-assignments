//
//  ContentView.swift
//  ListViewDemo
//
//  Created by sky on 2022/9/30.
//

import SwiftUI

struct ContentView: View {
    @State private var items: [Item] = []
    @Environment(\.colorScheme) var scheme

    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false

    @State private var listState = ListState()

    var body: some View {

        return VStack(spacing: 0) {

            ScrollView {
                PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                    VStack {
                        ForEach(items) { item in
                            ListItemView(item:item)
                        }
                    }
                }
            }
            .addPullToRefresh(isHeaderRefreshing: $headerRefreshing, onHeaderRefresh: reloadData,
                              isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMoreData)
            .environmentObject(listState)
            .background(Color(red: 0.945, green: 0.945, blue: 0.945))

        }
    }

    private func loadData() {
        var tempItems: [Item] = []
        var nomoredata:Bool = false
        for index in 0..<10 {
            if index >= itemsData.count {
                nomoredata = true
                break
            }
            let item = itemsData[index]

            tempItems.append(item)
        }

        listState.setNoMore(nomoredata)
        self.items = tempItems
    }

    private func reloadData() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            loadData()
            headerRefreshing = false
        }
    }

    private func loadMoreData() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            let startIndex = items.count
            for index in 0..<10 {
                let finalIndex = startIndex + index
                if finalIndex >= itemsData.count {
                    listState.setNoMore(true)
                    break
                }
                let item = itemsData[finalIndex]

                self.items.append(item)
            }
            footerRefreshing = false
        }
    }
}





// Model Data
var itemsData = [
    Item(name: "iJustine-iJustine-iJustine", desc: "Bug fixes and performance improvements", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Kaviya-Kaviya-Kaviya", desc: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Anna-Anna-Anna", desc: "From ad-hoc messaging to topic-based workstream collaboration", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Steve-Steve-Steve", desc: "Chat makes it easy to get work done where the conversation is happening", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Jenna-Jenna-Jenna", desc: "Group collaboration that allows Google Workspace content creation and sharing", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Creg-Creg-Creg", desc: "Docs, Sheets, Slides), without having to worry about granting permissions", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Tom Land-Tom Land", desc: "Side by side editors, one click meetings, scheduling, document creation", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Anna-Anna-Anna", desc: "and shared files, tasks, and events make it easy to get work done\n• Google search functionality", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Steve-Steve-Steve", desc: "with options to filter for conversations and content that you’ve shared", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "jenna-jenna-jenna", desc: "Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "哇咔咔-哇咔咔-哇咔咔", desc: "Admin Settings, Vault Retention, Holds, Search, and Export", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Creg-Creg-Creg", desc: "Message friends using text, photo, voice, video, location sharing, and more.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Tom Land-Tom Land", desc: "Create group chats with up to 500 members", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Anna-Anna", desc: "VOICE & VIDEO CALLS: High-quality voice and video calls to anywhere in the world.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Steve-Steve-Steve", desc: "Make group video calls with up to 9 people.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "jenna-jenna-jenna", desc: "REAL-TIME LOCATION: Not good at explaining directions? ", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Steve-Steve-Steve", desc: "Share your real-time location with the tap of a button.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Jenna-Jenna-Jenna", desc: "MOMENTS: Never forget your favorite moments.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Creg-Creg-Creg", desc: "videos, and more to share with friends on your personal Moments stream.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Tom Land-Tom Land", desc: "Share glimpses of your day. Record short videos to post in your Time Capsule before they disappear in 24 hours.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Anna-Anna-Anna", desc: "STICKER GALLERY: Browse thousands of fun, animated stickers to help express yourself in chats", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Steve-Steve-Steve", desc: "including stickers with your favorite cartoon and movie characters.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "jenna-jenna-jenna", desc: "Make chatting more unique with custom stickers and selfie stickers.", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
    Item(name: "Creg-Creg", desc: "MINI PROGRAMS: Countless third-party services all within the WeChat app that don’t require additional installation", image: "http://apng.onevcat.com/assets/elephant.png",isFavorite:false),
]
