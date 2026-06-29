//
//  AppItemList.swift
//  DemoApp
//
//  Created by dev on 2023/3/10.
//

import SwiftUI


struct AppItemList: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View{
        List(){
            ForEach(modelData.appInfos,id: \.trackId){ appInfo in
                AppItem(appInfo:appInfo)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color(.systemGray6))
            }
            HStack(){
                Spacer()
                if modelData.allDataIsLoaded {
                    Text(NSLocalizedString("NoMoredata",comment: ""))
                }else{
                    ProgressView()
                    Text(NSLocalizedString("Loading",comment: ""))
                        .foregroundColor(.gray)
                }
                Spacer()
            }
            .listRowBackground(Color(.systemGray6))
            .onAppear{
                modelData.loadMoreDataIfNeeded()
            }
            .refreshable {
                modelData.reloadData()
            }
            .listStyle(.plain)
            .background(Color(.systemGray6))
        }
    }
}


struct AppItemList_Previews: PreviewProvider {
    static var previews: some View {
        AppItemList().environmentObject(ModelData())
    }
}
