//
//  AppListView.swift
//  scdt_interview (iOS)
//
//  Created by Ray Tao on 2022/7/14.
//

import SwiftUI

struct AppListView: View {
    @EnvironmentObject var store: Store

    var list: AppState.AppList { store.appState.appList }

    var body: some View {
        ScrollView {
            ForEach(list.displayAppList(with: store.appState.user)) { app in
                AppInfoRow(
                    model: app
                )
            }
            Spacer()
                .frame(height: 20)
        }.navigationBarTitle("App")
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
