//
//  ContentView.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/17.
//

import SwiftUI

let PROCESS_VIEW_SIZE: CGFloat = 40
private let LIST_ITEM_HORIZONAL_PADDING: CGFloat = 20
let LOAD_FAILED_IMAGE: UIImage = UIImage(named: "scdt_load_failed")!
let LOAD_FAILED_MSG = "Load failed, please try it later."

struct ContentView: View {
    @EnvironmentObject var dataModel: ScdtAppDataModel
    
    private let listBackgroundColor = Color.gray.opacity(0.1)
        
    var body: some View {
        NavigationView {
            contentView
                .listStyle(.plain)
                .background(listBackgroundColor)
                .navigationTitle(Text("APP"))
        }
    }
    
    private var contentView: some View {
        HStack {
            let introducations = dataModel.appIntroductions
            let count = introducations.count
            if (count == 0) {
                if dataModel.loadFailed {
                    VStack {
                        Image(uiImage: LOAD_FAILED_IMAGE)
                            .resizable()
                            .frame(width: 200, height: 200)
                        Text(LOAD_FAILED_MSG)
                    }.onTapGesture {
                        dataModel.loadMoreIntroductions(true)
                    }
                } else {
                    ProgressView().frame(width: PROCESS_VIEW_SIZE, height: PROCESS_VIEW_SIZE)
                }
            } else {
                List(0...count, id: \.self) { index in
                    if index < count {
                        listItemView(introduction: introducations[index])
                    } else {
                        listLastView
                    }
                }.refreshable {
                    dataModel.loadMoreIntroductions()
                }
            }
        }
    }
    
    private func listItemView(introduction: ScdtAppIntroduction) -> some View {
        VStack {
            ScdtAppBriefIntroductionListItem().environmentObject(introduction)
        }.listRowInsets(EdgeInsets(top: 0, leading: LIST_ITEM_HORIZONAL_PADDING, bottom: 10, trailing: LIST_ITEM_HORIZONAL_PADDING))
            .listRowSeparator(.hidden)
            .listRowBackground(listBackgroundColor)
    }
    
    private var listLastView: some View {
        HStack {
            Spacer()
            if dataModel.loadFailed {
                HStack {
                    Spacer()
                    Image(uiImage: LOAD_FAILED_IMAGE)
                        .resizable()
                        .frame(width: 40, height: 40)
                    Text(LOAD_FAILED_MSG)
                    Spacer()
                }.onTapGesture {
                    dataModel.loadMoreIntroductions(true)
                }
            } else {
                if !dataModel.cantLoadMore {
                    ProgressView()
                        .frame(width: PROCESS_VIEW_SIZE, height: PROCESS_VIEW_SIZE)
                        .onAppear {
                            dataModel.loadMoreIntroductions()
                        }
                }
                Text (dataModel.cantLoadMore ? "No more data." : "Loading...")
                    .foregroundColor(.gray)
            }
            Spacer()
        }.listRowBackground(listBackgroundColor)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(ScdtAppDataModel())
    }
}
