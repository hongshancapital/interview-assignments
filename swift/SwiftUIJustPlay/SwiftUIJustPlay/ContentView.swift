//
//  ContentView.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import SwiftUI
import Combine
import SwiftUIFlux


struct ContentView: ConnectedView {
    
    @EnvironmentObject public var store: Store<AppState>
    struct Props {
        let appItems: [AppItemVM]
        let dispatch: DispatchFunction
    }
    
    @StateObject private var pageListener: HomePageListener = HomePageListener()
    
    func map(state: AppState, dispatch: @escaping DispatchFunction) -> Props {
        self.pageListener.dispatch = dispatch
        return Props(appItems: state.homeState.orderItems, dispatch: dispatch)
    }
    
    func body(props: Props) -> some View {
        
        NavigationView {
            List {
                ForEach(0..<props.appItems.count, id:\.self) { index in

                    AppPageCell(appId: index, item: props.appItems[index])
                        .background(Color.clear)

                }

                if !props.appItems.isEmpty {
                    Rectangle()
                        .foregroundColor(.clear)
                        .onAppear {
                            self.pageListener.currentPage += 1
                        }
                }
            }
            .onAppear() {
                if (self.pageListener.currentPage == 1) {
                    self.pageListener.loadPage()
                }
            }
            .refreshable {
                self.pageListener.refresh()
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
                                  state: AppState(homeState: HomeState(orderItems: [sampleAppVM])))
#endif

