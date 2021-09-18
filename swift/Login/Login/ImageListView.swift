//
//  ImageListView.swift
//  Login
//
//  Created by xiwang wang on 2021/9/16.
//

import SwiftUI
import QGrid

struct ImageListView: View {
    @State var columns: CGFloat = 10.0
    @State var vSpacing: CGFloat = 5.0
    @State var hSpacing: CGFloat = 5.0
    @State var vPadding: CGFloat = 0.0
    @State var hPadding: CGFloat = 0 //10.0
    
    //var controller: UIHostingController<Content> =
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                VStack(alignment:.center) {
                    QGrid(repeatElement(Person(), count:100),
                          columns: 10,
                          columnsInLandscape: 10,
                          vSpacing: self.vSpacing,
                          hSpacing: self.hSpacing,
                          vPadding: self.vPadding,
                          hPadding: self.hPadding) {_ in
                        GridCell()
                    }
                    Text("王修远小朋友\n2021.06.27-2021.10.07 百天纪念\n愿你日日平安 夜夜无忧\n百\"睡\"无忧")
                        .multilineTextAlignment(.center)
                        .font(.system(size: 13))
                        .onTapGesture {
                            
                            UIImageWriteToSavedPhotosAlbum(UIHostingController(rootView: self.body).image()!, nil, nil, nil)
                        }
                        
                }
            }
        }
    }
}

struct ImageListView_Previews: PreviewProvider {
    static var previews: some View {
        ImageListView()

    }
}

struct Person : Codable, Identifiable {
    var id: Int = 1
    var firstName: String = ""
    var lastName: String = ""
    var imageName: String = "1.jpg"
}

struct GridCell: View {
    //var person: Person
    
    var body: some View {
        VStack() {
            ImageStore.shared.image(name: "1")
                .resizable()
                .scaledToFit()
//                .clipShape(Circle())
//                .shadow(color: .primary, radius: 5)
                .padding([.horizontal], 7)
                .clipped()
                .frame(width: 35, height: 35, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            Text("第1天")
                .lineLimit(1)
                .font(.system(size: 8))
                .frame(width: 25, height: 15, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .foregroundColor(/*@START_MENU_TOKEN@*/.blue/*@END_MENU_TOKEN@*/)
            //      Text(person.lastName).lineLimit(1)
        }
        .font(.headline).foregroundColor(.white)
    }
}
