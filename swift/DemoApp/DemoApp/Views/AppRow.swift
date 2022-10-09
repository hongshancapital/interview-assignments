//
//  AppRow.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct AppRow: View {
    @State var appModel: AppModel
    @State var index: Int
    @EnvironmentObject var vm: ViewModel
    var body: some View {
        HStack{
            AsyncImage(url: URL(string: appModel.iconUrl)){ image in
                    image.resizable()
            }placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            VStack(alignment: .leading) {
                Text(appModel.name)
                    .font(.title)
                    .lineLimit(1)
                Text(appModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            Spacer()
            FavoriteButton(isSet: $vm.data[index].isFavorite)
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        AppRow(appModel: AppModel(
            id: 1,
            iconUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/60x60bb.jpg",
            name: "Google Chat",
            description: """
                    Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export
                    """
        ),
            index: 1)
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
