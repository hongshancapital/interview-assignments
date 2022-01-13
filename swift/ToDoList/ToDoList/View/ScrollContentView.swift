//
//  ScrollContentView.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

struct ScrollContentView: View {
    @ObservedObject var mainData: MainData

    var body: some View {
        ScrollView(.vertical, showsIndicators: false, content: {
            ForEach(mainData.searchTodo()) { groupModel in
                VStack {
                    ZStack {
                        HStack {
                            Text("\(groupModel.title)")
                                .foregroundColor(Color("CheckedColor"))
                                .font(.system(size: 16))
                                .fontWeight(.semibold)
                                .multilineTextAlignment(.leading)
                                .frame(height: 44.0)
                                .padding(.leading)
                            Spacer()
                        }
                    }
                    ForEach(groupModel.toDoList) { value in
                        VStack {
                            TodoViewCell(mainData: mainData, groupModel: groupModel, toDoModel: value)
                        }
                    }
                }
            }
            .padding(.bottom, 60.0)
        })
    }
}
