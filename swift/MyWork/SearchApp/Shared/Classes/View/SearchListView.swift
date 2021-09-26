//
//  SwiftUIView.swift
//  
//
//  Created by Changgeng Wang on 2021/9/26.
//

import SwiftUI


public enum RowStyle: Hashable {
    case OutOfStockRow
    case InStockRow 
}

public struct SectionItem: Hashable {
    var name:String
    var rows:[RowItem] = []
}

public struct RowItem: Hashable {
    var name:String
    var detailName:String
    var descString:String
    var sectionName:String
    var style:RowStyle
    
}

/// 查询列表
public struct SearchListView: View {
    @Binding var sections:[SectionItem]
    @Binding var hasMore:Bool
    var showMoreAction: (() -> Void)

    public var body: some View {
        
        if sections.isEmpty {
            VStack {
                ZStack {
                    Color.clear
                    Text("No Result")
                }
            }
        } else {
            List {
                ForEach(sections, id: \.self) { section in
                    Section(header: Text(section.name).foregroundColor(Color.gray)) {
                        ForEach(section.rows, id: \.self) { row in
                            SearchListRowView(row: .constant(row))
                        }
                    }
                }
                if self.hasMore {
                    ZStack {
                        Color.clear
                        Button {
                            showMoreAction()
                        } label: {
                            Text("Show More").foregroundColor(Color.blue)
                        }
                    }
                } else {
                    ZStack {
                        Color.clear
                        Text("No more data")
                    }
                }
            }.listStyle(PlainListStyle())
        }
    }
}

public struct SearchListRowView: View {
    @Binding var row:RowItem
    public var body: some View {
        HStack {
            VStack(alignment:.leading) {
                Text(row.name).font(.headline)
                Spacer()
                Text(row.detailName).font(.footnote)
            }
            
            HStack {
                Spacer()
                VStack {
                    HStack {
                        Text(row.descString)
                            .font(.footnote)
                            .foregroundColor(priceColor())
                            .padding(.leading, 10)
                            .padding(.trailing, 10)
                            .padding(.top, 5)
                            .padding(.bottom, 5)

                    }
                    .background(Color(red: 0xD2/255, green: 0xD2/255, blue: 0xD2/255))
                    .cornerRadius(10)
                }
            }
        }
        .frame(height: 44)
    }
    
    func priceColor() -> Color
    {
        if self.row.style == .OutOfStockRow {
            return Color.gray
        }else {
            return Color.blue
        }
    }
}


