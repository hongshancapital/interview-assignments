//
//  ContentView.swift
//  SCDT-Demo
//
//  Created by wuzhe on 10/17/22.
//

import SwiftUI

struct ContentView: View {
    @StateObject private var appListViewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            Group{
                if appListViewModel.appInfomations.count == 0{
                    ProgressView()
                        .onAppear{
                            appListViewModel.reload()
                        }
                }
                else{
                    appListView
                }
            }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
    
    private var appListView: some View{
        List {
            ForEach(appListViewModel.appInfomations){appInfomation in
                AppCardView(appInfo: appInfomation)
                    .appListCellStyle()
            }
            
            footView
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .overlay(
            errorMessageOverlay
        )
        .refreshable {
            appListViewModel.reload()
        }
    }
    
    private var footView: some View{
        HStack{
            Spacer()
            if appListViewModel.appInfomations.count > 0 {
                if appListViewModel.hasMore {
                    ProgressView().mediumSize()
                    Text("Loading...")
                        .fontWeight(.light)
                        .padding(.leading,8)
                        .onAppear{
                            appListViewModel.loadMore()
                        }
                }
                else{
                    Text("No more data")
                        .fontWeight(.light)
                }
            }
            Spacer()
        }
        .foregroundColor(Color.gray)
        .listRowBackground(EmptyView())
    }
    
    private var errorMessageOverlay: some View{
        Group{
            if appListViewModel.error != nil{
                if appListViewModel.appInfomations.count == 0 {
                    VStack{
                        Text(appListViewModel.error!.description)
                            .padding()
                        Button(action: {appListViewModel.reload()}, label: {
                            Text("Tap to retry")
                        })
                    }
                }
                else{
                    Text(appListViewModel.error!.description)
                        .padding()
                        .background(Color.white)
                        .onAppear(){
                            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                                appListViewModel.error = nil
                            }
                        }
                }
            }
        }
    }
}

extension ProgressView {
    func mediumSize() -> some View{
        scaleEffect(37.0 / 34.0)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .previewDevice("iPhone 13 Pro Max")
    }
}
