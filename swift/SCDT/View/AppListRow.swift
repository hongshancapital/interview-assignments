//
//  AppListRow.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import SwiftUI

struct AppListRow: View {
    @EnvironmentObject var viewModel: ApplicationViewModel
    var appIndex: Int {
        viewModel.applications.firstIndex(where: { $0.id == info.id }) ?? 0
    }
    var info: Application
    var body: some View {
        HStack() {
            IconView(url: info.iconUrl ?? "")
            VStack(alignment: .leading) {
                Text(info.name ?? "").lineLimit(1).font(.title2)
                Spacer()
                Text(info.description ?? "").lineLimit(2).font(.system(size: 14))
            }
            Spacer()
            Button(action: {
                withAnimation(.easeIn(duration: 0.3)) {
                    self.viewModel.applications[appIndex].isFavorite.toggle()
                }
                self.viewModel.syncUserDefault()
            }) {
                Image(systemName: info.isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(info.isFavorite ? Color.red : Color.gray).scaleEffect(info.isFavorite ? 1.2 : 1)
            }
            .buttonStyle(PlainButtonStyle())
        }
        .padding(.vertical, 5)
    }
}

struct AppListRow_Preview: PreviewProvider {
    static var previews: some View {
        AppListRow(info: Application(id: 1,name: "Bumble - Dating. Friends. Bizz", iconUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/5d/fb/e8/5dfbe8f2-722a-e8f9-72da-f1479961ee28/AppIcon-1x_U007emarketing-0-7-0-85-220.png/60x60bb.jpg", description: "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections."))
    }
}
