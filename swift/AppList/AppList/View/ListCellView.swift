//
//  ListCellView.swift
//  AppList
//
//  Created by mengyun on 2022/3/11.
//

import SwiftUI

struct ListCellView: View {
    
    var index: Int = 0
    var cellData: ListCellDataProtocol
    
    @State var isFav: Bool = false
    @State private var remoteImage: UIImage?
    
    @EnvironmentObject var vm: ListViewModel
    
    var body: some View {
        HStack {
            image
            contents
            Spacer()
            favsView
        }
        .padding()
        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
        .background(Color.white)
        .cornerRadius(8)
        .onAppear {
            
        }
    }
    
    private var image: some View {
        VStack {
            if let uiImage = remoteImage {
                Image(uiImage: uiImage)
                    .resizable()
                    .frame(width: 60, height: 60)
                    .aspectRatio(contentMode: .fit)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .overlay {
                        RoundedRectangle(cornerRadius: 12, style: .continuous)
                            .stroke(Color(red: 0.8, green: 0.8, blue: 0.8), lineWidth: 1)
                    }
            } else {
                ProgressView()
                    .frame(width: 60, height: 60)
                    .onAppear {
                        fetchRemoteImage(url: URL(string: cellData.artworkUrl))
                    }
            }
        }
    }
    
    private var contents: some View  {
        return VStack(alignment: .leading, spacing: 3.0) {
            Text(cellData.trackName)
                .lineLimit(1)
                .font(.title)
                //.background(.blue)

            Text(cellData.description).lineLimit(2)
                .font(.footnote)
                .offset(x: 2)
                //.background(.green)
        }
        .multilineTextAlignment(.leading)
        .frame(idealWidth: UIScreen.main.bounds.width - 188, alignment: .leading)
        .fixedSize(horizontal: true, vertical: false)
    }
    
    private var favsView: some View  {
        let indexStr = "\(cellData.trackId)"
        return Image(systemName: isFav ? "heart.fill" : "heart")
            .scaleEffect(isFav ? 1.4 : 1.2)
            .foregroundColor(isFav ? .red : .gray)
            .onTapGesture {
                isFav = !isFav
                vm.favs[indexStr] = isFav
                vm.saveFavoriteData()
            }
    }
    
    func fetchRemoteImage(url: URL?)
    {
        guard let url = url else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if let image = UIImage(data: data!){
                self.remoteImage = image
            }
            else{
                print(error ?? "")
            }
        }.resume()
        
    }
}

struct ListCellView_Previews: PreviewProvider {
    static var previews: some View {
        let cd = ListCellModel(
            trackId: 0,
            artworkUrl60: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/d8/aa/f2/d8aaf25d-b0a8-cc53-6cf2-e24301342f34/source/60x60bb.jpg",
            trackName: "Bumble - Dating. Friends. Bizz",
            description: "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections.")
        ListCellView(cellData: cd)
    }
}
