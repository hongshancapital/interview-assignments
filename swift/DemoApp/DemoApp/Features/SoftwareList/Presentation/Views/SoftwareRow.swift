//
//  SoftwareRow.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import SwiftUI

struct SoftwareRow: View {
    @EnvironmentObject private var viewModel: SoftwareListViewModel
    @Binding var software: Software
    
    var body: some View {
        HStack {
            AsyncImage(
                url: URL(string: software.artworkUrl100)) { image in
                    image.resizable()
                        .aspectRatio(contentMode: .fit)
                        .clipShape(RoundedRectangle(cornerRadius: 8))
                        .overlay {
                            RoundedRectangle(cornerRadius: 8).stroke(
                                Color.gray.opacity(0.3),
                                lineWidth: 0.5
                            )
                        }
                    
                } placeholder: {
                    ProgressView()
                        .frame(maxWidth: 60, maxHeight: 60)
                }
                .padding([.leading, .top, .bottom], 12)
            VStack(alignment: .leading, spacing: 4) {
                Text(software.trackCensoredName)
                    .lineLimit(1)
                    .font(.system(size: 16))
                    .bold()
                Text(software.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }
            .padding([.trailing, .leading], 4)
            Spacer()
            Button {
                viewModel.changeSoftwareIsLike(software)
            } label: {
                Image(systemName: software.isLike ? "heart.fill" : "heart")
                    .scaleEffect(.init(width: 1.2, height: 1.2))
                    .foregroundColor(software.isLike ? .red : .gray)
            }
            .padding([.trailing], 24)
        }
        .frame(height: 80)
        .background(.white)
        .cornerRadius(8)
    }
}

struct SoftwareRow_Previews: PreviewProvider {
    static var previews: some View {
        SoftwareRow(software: .constant(Software.default))
            .previewLayout(.fixed(width: 375, height: 40))
    }
}
