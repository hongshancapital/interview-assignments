//
//  ContentView.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/4/24.
//

import SwiftUI

/*
 主视图
 */
struct ContentView: View {
    @EnvironmentObject var store: Store
    
    var body: some View {
        if(store.appState.appInfoModels.count > 0) {
            List() {
                ForEach(store.appState.appInfoModels, id: \.id) { appInfoViewModel in
                    ContentInfoRow.init(model: appInfoViewModel)
                        .frame(height: 70)
                        .listRowBackground(Color.clear)
                        .background(Color.white)
                        .cornerRadius(10)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 6, leading: 0, bottom: 6, trailing: 0))
                }
                ///底部视图
                BottomIndicatorView()
                    .listRowBackground(Color.clear)
                    .onAppear {
                        switch store.appState.appDataLoadingState {
                        case .loadFinish:
                            store.dispatch(.loadNextPage)
                        default:
                            break
                        }
                    }.background(Color.clear)
            }
            .refreshable {
                store.dispatch(.refreshData)
            }.alert(isPresented: $store.appState.displayAlert) {
                let wording = store.appState.error?.localizedDescription ?? ""
                return Alert(title: Text("Ooops! Sth went wrong(\(wording)"))
            }
            
        } else {
            ///首次进入等待视图
            ProgressView()
                .progressViewStyle(.circular)
                .onAppear {
                    store.dispatch(.refreshData)
                }.alert(isPresented: $store.appState.displayAlert) {
                    let wording = store.appState.error?.localizedDescription ?? ""
                    return Alert(title: Text("Ooops! Sth went wrong(\(wording)"))
                }
        }
        
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
