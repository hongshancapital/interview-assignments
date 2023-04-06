
//
//  ContentView.swift
//  swiftUI
//
//  Created by pchen on 2023/4/3.
//

import SwiftUI
import Combine

struct AppContentView: View {
    
    @StateObject  var appVM = AppViewModel()
    @State private var listPullState:ListPullStateEnum = .none
    @State private var isInitState = true
    
    var body: some View {
        
        ZStack {
            NavigationView {
                listView()
            }
            .background(Color(.secondarySystemBackground))
            .task {
                await refreshApp()
            }
            if isInitState {
                ProgressView().scaleEffect(1.5)
            }
        }
    }
    
    func listView() -> some View {
        
        List($appVM.appModels) { appModel in
            listItemView(appModel: appModel)
        }
        .listStyle(.plain)
        .navigationTitle("App")
        .refreshable(action: {
            await refreshApp()
        })
        .pullUpLoading(listPullState: $listPullState, onLoad: {
            loadMore()
        })
        .background(Color(.secondarySystemBackground))
    }
    
    func listItemView(appModel: Binding<AppModel>) -> some View {
        
        HStack(alignment: .center, content: {
            AsyncImage(url: URL(string: appModel.artworkUrl60.wrappedValue)) { phase in
                if let image = phase.image {
                    image.resizable().cornerRadius(8).aspectRatio(contentMode: .fit)
                } else if phase.error != nil {
                    //todo error handle
                } else {
                    ProgressView()
                }
            }
            .frame(width: 50, height: 50)
        
            VStack(alignment: .leading, content: {
                Text(appModel.trackName.wrappedValue).font(.headline).padding(.bottom,1.0).lineLimit(1)
                
                Text(appModel.description.wrappedValue).font(.caption2).lineLimit(2)
            })
            
            Spacer()
            
            HearView(isSelected: appModel.isCollectioned) { selected in
            }
        })
        .padding(10)
        .background(Color.white)
        .cornerRadius(8)
        .listRowSeparator(.hidden)
        .listRowBackground(Color(.secondarySystemBackground))
        .listRowInsets(EdgeInsets.init(top: 15, leading: 20, bottom: 0, trailing: 20))
    }
    
    func loadMore() {
        
        self.listPullState = .isLoadMoreing
        appVM.nextPageAppModels { appmodels, error in
            if (error != nil && appmodels.count == 0) {
                self.listPullState = .noMoreState
            }else {
                self.listPullState = .none
            }
        }
    }
    
    func refreshApp() async {
        
        do {
            try await appVM.refreshAppModels()
            self.listPullState = .none
            self.isInitState = false
        }catch {
            print(#function, error)
            self.listPullState = .none
            self.isInitState = false
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        AppContentView()
    }
}



