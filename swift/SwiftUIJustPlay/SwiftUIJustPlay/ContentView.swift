//
//  ContentView.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import SwiftUI
import SwiftUIFlux


struct ContentView: ConnectedView {
    
    @EnvironmentObject private var store: Store<AppState>
    struct Props {
        let appItems: [AppItemVM]
        let dispatch: DispatchFunction
    }
    
    let pageListener: HomePageListener = HomePageListener()
    
    @State private var currentPage = 1
    let pageSize: Int = 20
    
    
    func map(state: AppState, dispatch: @escaping DispatchFunction) -> Props {
        Props(appItems: state.homeState.appItems.map { $0.value },
              dispatch: dispatch)
    }
    
    func body(props: Props) -> some View {
        NavigationView {
            List {
                ForEach(props.appItems) { model
                    in
                    AppPageCell(appId: model.id, item: model)
                        .background(Color.clear)
                        .padding(
                            EdgeInsets(
                                top: 0,
                                leading: 0,
                                bottom: 0,
                                trailing: 5
                            )
                        )
                }
                
                if !props.appItems.isEmpty {
                    Rectangle()
                        .foregroundColor(.clear)
                        .onAppear {
                            self.currentPage += 1
                            props.dispatch(JustPlayActions.FetchAppStoreList(page: PageEndPoint.pageNo(pageIndex: self.currentPage, pageSize: self.pageSize)))
                        }
                }
            }.navigationTitle("App")
        }
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity
        ).onAppear {
            if self.currentPage == 1 {
                props.dispatch(JustPlayActions.FetchAppStoreList(page: PageEndPoint.pageNo(pageIndex: self.currentPage, pageSize: self.pageSize)))
            }
        }
    }
}


#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(sampleStore)
    }
}


let sampleAppItem =
AppItem(id: 1, title: "123123", body: "1231231231234124124124124", favorite: true, imageURLString: "PlaceHolderIcon")

let sampleAppVM = AppItemVM(appItem: sampleAppItem)


let sampleStore = Store<AppState>(reducer: appStateReducer,
                                  state: AppState(homeState: HomeState(appItems: [1 : sampleAppVM])))
#endif

