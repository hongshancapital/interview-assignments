//
//  AppList.swift
//  scdtswift
//
//  Created by esafenet on 2023/2/13.
//

import SwiftUI

struct AppList: View {
    @ObservedObject var viewModel: AppListModel
    var body: some View {
        List {
            if viewModel.isLoading {
                ProgressView()
            }
            ForEach(viewModel.data) { item in
                AppRow(data: item)
            }
        }
        
        
    }
    
}

struct AppList_Previews: PreviewProvider {
    static let data_preview: AppListModel = {
        let applistmodel = AppListModel()
        applistmodel.getApplist()
        return applistmodel
    }()
    
    static var previews: some View {
        AppList(viewModel: data_preview)
    }
}
