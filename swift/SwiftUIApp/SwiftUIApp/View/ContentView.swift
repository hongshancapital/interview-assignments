//
//  ContentView.swift
//  SwiftUIApp
//
//  Created by quanhai on 2022/6/28.
//

import SwiftUI


struct ContentView: View {
	@StateObject private var appVm = AppViewModel()
	
    var body: some View {
		NavigationView {
			if appVm.appList.count == 0 && appVm.hasMoreData{
				// 加载
				ProgressView()
					.onAppear(perform: {
						Task{
							 try? await appVm.getAppList()
						}
					})
			}else if appVm.appList.count == 0{
				// 无数据
				VStack(alignment: .center){
					Text("Empty data.")
				}
			}else{
				List{
					ForEach(appVm.appList){app in
						AppTableCell(appModel: app)
							.listRowSeparator(.hidden)
							.listRowBackground(EmptyView())
					}

					HStack{
						Spacer()
						if appVm.isFecthing{
							ProgressView()
									.padding(.trailing, 8)
							Text("Loading...")
								.foregroundColor(Color(uiColor: .lightGray))
						}else if !appVm.hasMoreData{
							Text("No more data.")
								.foregroundColor(Color(uiColor: .lightGray))
						}
						Spacer()
					}
						.padding(.top, 4)
						.listRowSeparator(.hidden)
						.listRowBackground(EmptyView())
						.onAppear(perform: {
							Task{
									try? await appVm.getMoreAppList()
								}
						})
				}
				.navigationTitle("App")
				.background(Color("appListBackground"))
				.listStyle(.plain)
				.refreshable {
					Task{
						try? await appVm.getMoreAppList()
					}
				}
				.environmentObject(appVm)
			}
		}
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
