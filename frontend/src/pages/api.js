import axios from 'axios';
export const deleteItem = async id => {
  const response = await axios.delete(`items/${id}`);
};
