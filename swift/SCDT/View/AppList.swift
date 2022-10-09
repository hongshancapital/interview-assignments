//
//  AppList.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import SwiftUI

struct AppList: View {
    @EnvironmentObject private var viewModel: ApplicationViewModel
    init() {
        UITableView.appearance().sectionHeaderHeight = .zero
    }
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.applications) { application in
                    Section(footer: footer(shouldDisplay: viewModel.applications.last == application).environmentObject(viewModel)) {
                        AppListRow(info: application).environmentObject(self.viewModel)
                            .onAppear(){
                                if (viewModel.applications.last == application) {
                                    Task {
                                        await viewModel.fetchApplications()
                                    }
                                }
                            }
                    }
                }
            }.refreshable {
                await viewModel.refresh()
            }.listStyle(InsetGroupedListStyle())
                .navigationBarTitle(Text("App"))
        }
    }
}
struct footer: View {
    @EnvironmentObject var viewModel: ApplicationViewModel
    var shouldDisplay: Bool
    var body: some View {
        if shouldDisplay {
            HStack {
                Spacer()
                if viewModel.isLoading {
                    ProgressView()
                    Text("Loading...").padding(.leading, 5)
                } else {
                    Text("No more data.")
                }
                Spacer()
            }
        }
    }
}

struct LandmarksList_Previews: PreviewProvider {
    static var previews: some View {
        AppList().environmentObject(ApplicationViewModel())
    }
}
