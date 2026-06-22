//
//  ContentView.swift
//  scdtswift
//
//  Created by esafenet on 2023/2/10.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var viewModel = AppListModel()
    var body: some View {
        NavigationView{
            AppList(viewModel: viewModel)
                .navigationTitle("App")
                .navigationBarTitleDisplayMode(.automatic)
                
        }
        .onAppear{
            viewModel.getApplist()
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static let appListVM: AppListModel = {
        let applistmodel = AppListModel()
        applistmodel.data = applistPreviewData
        return applistmodel
    }()
    
    
    static var previews: some View {
        ContentView()
    }
}
