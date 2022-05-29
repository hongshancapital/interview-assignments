//
//  ListItemView.swift
//  InterviewPreWork
//
//  Created by jeffy on 2022/5/22.
//

import SwiftUI

struct ListItemView: View {
    var item: AppItem
    @State var liked: Bool = false
    
    var body: some View {
        HStack {
            // icon
            AsyncImage(url: item.artworkUrl60) { img in
                img.resizable()
                    .aspectRatio(contentMode: .fill)
                    .overlay(
                        RoundedRectangle(cornerRadius: 16, style: .continuous)
                            .stroke(.gray, lineWidth: 0.5)
                    )
            } placeholder: {
                ProgressView()
            }
            .frame(width: 64, height: 64, alignment: .center)
            .cornerRadius(16)
            
            // trackName and description
            VStack(alignment: .leading) {
                Text(item.trackName)
                    .lineLimit(1)
                    .font(.headline)
                Spacer()
                Text(item.description)
                    .lineLimit(2)
                    .font(.caption)
            }
            // Spacer
            Spacer()
            // heart button
            Button {
                liked.toggle()
            } label: {
                Image(systemName: liked ? "heart.fill" : "heart")
                    .foregroundColor(liked ? .red : .gray)
                    .scaleEffect(liked ? 1.2 : 1.0)
            }
            .frame(width: 44, height: 44, alignment: .center)
            .animation(.interpolatingSpring(stiffness: 50, damping: 5), value: liked)
        }
        .padding(EdgeInsets(top: 16, leading: 16, bottom: 16, trailing: 8))
        .background(.white)
        .cornerRadius(16)
    }
}

struct TestView: View {
    
    let a = AppItem(id: 001, trackName: "NiceTang1", description: "nice htfg fang yeyeye tantian nana wo zhongying shi afandemei", artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/fd/34/86/fd3486e7-8b0b-04ed-68ce-668259b28cac/contsched.wdyxywek.png/60x60bb.jpg")!)
    
    let b = AppItem(id: 002, trackName: "NiceTang2", description: "nice htfg\n\n", artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/fd/34/86/fd3486e7-8b0b-04ed-68ce-668259b28cac/contsched.wdyxywek.png/60x60bb.jpg")!)
    
    var body: some View {
        ScrollView {
            ForEach([a, b]) { item in
                Section {
                    ListItemView(item: item)
                }
                .listSectionSeparator(.hidden, edges: .all)
            }
        }
        .listStyle(.inset)
        .padding([.leading, .trailing], 10)
        .background(.gray)
    }
}

struct ListItemView_Previews: PreviewProvider {
    static var previews: some View {
        TestView()
    }
}
