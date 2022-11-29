//
//  HomeList.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import SwiftUI

struct HomeList: View {
    
    
    var apps: [AppModel] = {
        var apps: [AppModel] = []
        for index in 0...10 {
            apps.append(
                AppModel(
                    bundleId: "com.google.chat\(index)",
                    trackCensoredName: "Google Chat",
                    artworkUrl60: "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/4c/5d/14/4c5d1486-6977-4cf7-668b-c223465d572b/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg",
                    description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export")
            )
        }
        return apps
    }()
    
    let tableBackgroundColor = UIColor.systemGray6
    
    init() {
        UITableView.appearance().separatorStyle = .none
        UITableView.appearance().separatorColor = .clear
        UITableView.appearance().backgroundColor = tableBackgroundColor
        UITableViewCell.appearance().backgroundColor = tableBackgroundColor
    }

    var body: some View {
        ZStack {
            Color(tableBackgroundColor).edgesIgnoringSafeArea(.all)
            List {
                ForEach(apps) { app in
                    if #available(iOS 15.0, *) {
                        listRow(app: app)
                            .listRowSeparator(.hidden)
                    } else {
                        listRow(app: app)
                    }
                }
            }
            .listStyle(.plain)
        }
    }
    
    func listRow(app: AppModel) -> some View {
        let content = VStack {
            HomeRow(model: app)
                .frame(height: 80)
                .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
                .background(Color.white)
                .cornerRadius(10)
        }.listRowBackground(Color(tableBackgroundColor))
        return content
    }
}

struct HomeList_Previews: PreviewProvider {
    static var previews: some View {
        HomeList()
    }
}
