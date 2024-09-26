//
//  AppListRow.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import SwiftUI

struct AppRow: View {

    private var app: ChatApp
    
    private var markProcessor: MarkProcessor?
    init(app: ChatApp, markProcessor: MarkProcessor?) {
        self.app = app
        self.markProcessor = markProcessor
    }
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: app.iconUrl)) { image in
                image.resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .frame(width: 60, height: 60, alignment: .center)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 1.5)
                            .opacity(0.2)
                    }
            } placeholder: {
                ProgressView()
            }
            .frame(width: 60, height: 60)
            
            VStack(alignment: .leading, spacing: 5) {
                Text(app.name)
                    .font(.headline)
                    .lineLimit(1)
                Text(app.description)
                    .font(.footnote)
                    .lineLimit(2)
            }
            
            Spacer()
            
            MarkButton(marked: app.isMarked, markProcessor: markProcessor)
        }
        
    }
}

struct AppListRow_Previews: PreviewProvider {
    static var previews: some View {
        AppRow(app: ChatApp(id: 1, name: "Google Chat", iconUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/2c/ff/0f/2cff0f08-85a7-898d-d209-6f47e43d8ac5/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg", description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export"), markProcessor: nil)
    }
}
