//
//  HSAppListView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Combine
import SwiftUI

struct HSAppListView: View {
    @State var hasMore: Bool = true
    @ObservedObject var dataController: HSAppListDataController = HSAppListDataController()

    init() {
        UITableView.appearance().separatorColor = .clear
        UITableViewCell.appearance().selectionStyle = .none
        UIWindow.appearance().tintColor = UIColor.white
        let coloredAppearance = UINavigationBarAppearance()
        coloredAppearance.backgroundColor = .white
        coloredAppearance.largeTitleTextAttributes = [
            NSAttributedString.Key.foregroundColor: UIColor.black,
            NSAttributedString.Key.font: UIFont(name: "PingFangSC-Medium", size: 40) ?? UIFont.systemFont(ofSize: 40)]
        UINavigationBar.appearance().standardAppearance = coloredAppearance
    }

    var body: some View {
        NavigationView {
            VStack {
                switch dataController.loadState {
                case .noDeter:
                    HSLoadingView()
                case .empty:
                    HSEmptyView()
                case .error:
                    HSNetworkErrorView()
                        .offset(y: -50)
                        .onTapGesture {
                            dataController.loadData(more: false)
                        }
                case .complete:
                    RefreshListView(content: {
                        ForEach(dataController.items, id: \.self) { appInfo in
                            HSHomeAppListCell(appInfo: appInfo)
                        }
                    }, refreshAction: {
                        dataController.loadData(more: false)
                    }, footerAction: {
                        dataController.loadData(more: true)
                    }, hasMore: $dataController.hasMore)
                        .listStyle(.plain)
                }
            }
            .navigationBarTitle("App")
            .background(Color.background)
        }
        .navigationViewStyle(.stack)
        .onAppear {
            dataController.loadData(more: false)
            UITableViewCell.appearance().selectionStyle = .none
        }
    }
}

struct HSAppListView_Previews: PreviewProvider {
    static var previews: some View {
        HSAppListView()
    }
}
