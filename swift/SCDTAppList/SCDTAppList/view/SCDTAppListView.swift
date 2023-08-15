//
//  ContentView.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct SCDTAppListView: View {
    var body: some View {
        NavigationView{
            PageLoadingListView<SCDTAppListViewModel> (viewModel:SCDTAppListViewModel() ){ index, item, vm in
                AnyView(
                    AppProductItemRow(model: Binding(
                        get: { item },
                        set: { _ in
                            Task(priority: .userInitiated) {
                                await vm.favoriteStatusSync(item: item)
                            }
                        }))
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 6, leading: 0, bottom: 6, trailing: 0))
                        .listRowBackground(Color.clear)
                )
                
            }
            .navigationBarTitle(
                Text("App")
            )
        }
        //root view background color
        .foregroundColor(SwiftUI.Color("MainViewBackgroundColor"))
        .navigationViewStyle(.stack)
    }
    
}

#if DEBUG
struct SCDTAppListView_Previews: PreviewProvider {
    static var previews: some View {
        SCDTAppListView()
    }
}
#endif
